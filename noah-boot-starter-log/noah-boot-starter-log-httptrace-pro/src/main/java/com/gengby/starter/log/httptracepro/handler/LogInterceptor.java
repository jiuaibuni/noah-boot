/*
 * MIT License
 *
 *  Copyright (c) 2024 久爱不腻gby
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in all
 *  copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *  SOFTWARE.
 */

package com.gengby.starter.log.httptracepro.handler;

import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.alibaba.ttl.TransmittableThreadLocal;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.lang.NonNull;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import com.gengby.starter.log.core.annotation.Log;
import com.gengby.starter.log.core.dao.LogDao;
import com.gengby.starter.log.core.enums.Include;
import com.gengby.starter.log.core.model.LogRecord;
import com.gengby.starter.log.core.model.LogResponse;
import com.gengby.starter.log.httptracepro.autoconfigure.LogProperties;

import java.time.Clock;
import java.util.Set;

/**
 * 日志拦截器
 *
 * @author Noah
 * @since 1.1.0
 */
public class LogInterceptor implements HandlerInterceptor {

    private static final Logger log = LoggerFactory.getLogger(LogInterceptor.class);
    private final LogDao logDao;
    private final LogProperties logProperties;
    private final TransmittableThreadLocal<LogRecord.Started> timestampTtl = new TransmittableThreadLocal<>();

    public LogInterceptor(LogDao logDao, LogProperties logProperties) {
        this.logDao = logDao;
        this.logProperties = logProperties;
    }

    @Override
    public boolean preHandle(@NonNull HttpServletRequest request,
                             @NonNull HttpServletResponse response,
                             @NonNull Object handler) {
        Clock timestamp = Clock.systemUTC();
        if (this.isRequestRecord(handler, request)) {
            if (Boolean.TRUE.equals(logProperties.getIsPrint())) {
                log.info("[{}] {}", request.getMethod(), request.getRequestURI());
            }
            LogRecord.Started startedLogRecord = LogRecord.start(timestamp, new RecordableServletHttpRequest(request));
            timestampTtl.set(startedLogRecord);
        }
        return true;
    }

    @Override
    public void afterCompletion(@NonNull HttpServletRequest request,
                                @NonNull HttpServletResponse response,
                                @NonNull Object handler,
                                Exception e) {
        LogRecord.Started startedLogRecord = timestampTtl.get();
        if (null == startedLogRecord) {
            return;
        }
        timestampTtl.remove();
        try {
            HandlerMethod handlerMethod = (HandlerMethod)handler;
            Log methodLog = handlerMethod.getMethodAnnotation(Log.class);
            Log classLog = handlerMethod.getBeanType().getDeclaredAnnotation(Log.class);
            Set<Include> includeSet = this.getIncludes(methodLog, classLog);
            LogRecord finishedLogRecord = startedLogRecord.finish(new RecordableServletHttpResponse(response, response
                .getStatus()), includeSet);
            // 记录日志描述
            if (includeSet.contains(Include.DESCRIPTION)) {
                this.logDescription(finishedLogRecord, methodLog, handlerMethod);
            }
            // 记录所属模块
            if (includeSet.contains(Include.MODULE)) {
                this.logModule(finishedLogRecord, methodLog, classLog, handlerMethod);
            }
            if (Boolean.TRUE.equals(logProperties.getIsPrint())) {
                LogResponse logResponse = finishedLogRecord.getResponse();
                log.info("[{}] {} {} {}ms", request.getMethod(), request.getRequestURI(), logResponse
                    .getStatus(), finishedLogRecord.getTimeTaken().toMillis());
            }
            logDao.add(finishedLogRecord);
        } catch (Exception ex) {
            log.error("Logging http log occurred an error: {}.", ex.getMessage(), ex);
        }
    }

    /**
     * 获取日志包含信息
     *
     * @param methodLog 方法级 Log 注解
     * @param classLog  类级 Log 注解
     * @return 日志包含信息
     */
    private Set<Include> getIncludes(Log methodLog, Log classLog) {
        Set<Include> includeSet = logProperties.getIncludes();
        if (null != classLog) {
            this.processInclude(includeSet, classLog);
        }
        if (null != methodLog) {
            this.processInclude(includeSet, methodLog);
        }
        return includeSet;
    }

    /**
     * 处理日志包含信息
     *
     * @param includes      日志包含信息
     * @param logAnnotation Log 注解
     */
    private void processInclude(Set<Include> includes, Log logAnnotation) {
        Include[] includeArr = logAnnotation.includes();
        if (includeArr.length > 0) {
            includes.addAll(Set.of(includeArr));
        }
        Include[] excludeArr = logAnnotation.excludes();
        if (excludeArr.length > 0) {
            includes.removeAll(Set.of(excludeArr));
        }
    }

    /**
     * 记录描述
     *
     * @param logRecord     日志信息
     * @param methodLog     方法级 Log 注解
     * @param handlerMethod 处理器方法
     */
    private void logDescription(LogRecord logRecord, Log methodLog, HandlerMethod handlerMethod) {
        // 例如：@Log("新增部门") -> 新增部门
        if (null != methodLog && CharSequenceUtil.isNotBlank(methodLog.value())) {
            logRecord.setDescription(methodLog.value());
            return;
        }
        // 例如：@Operation(summary="新增部门") -> 新增部门
        Operation methodOperation = handlerMethod.getMethodAnnotation(Operation.class);
        if (null != methodOperation) {
            logRecord.setDescription(CharSequenceUtil.blankToDefault(methodOperation.summary(), "请在该接口方法上指定日志描述"));
        }
    }

    /**
     * 记录模块
     *
     * @param logRecord     日志信息
     * @param methodLog     方法级 Log 注解
     * @param classLog      类级 Log 注解
     * @param handlerMethod 处理器方法
     */
    private void logModule(LogRecord logRecord, Log methodLog, Log classLog, HandlerMethod handlerMethod) {
        // 例如：@Log(module = "部门管理") -> 部门管理
        if (null != methodLog && CharSequenceUtil.isNotBlank(methodLog.module())) {
            logRecord.setModule(methodLog.module());
            return;
        }
        if (null != classLog && CharSequenceUtil.isNotBlank(classLog.module())) {
            logRecord.setModule(classLog.module());
            return;
        }
        // 例如：@Tag(name = "部门管理") -> 部门管理
        Tag classTag = handlerMethod.getBeanType().getDeclaredAnnotation(Tag.class);
        if (null != classTag) {
            String name = classTag.name();
            logRecord.setModule(CharSequenceUtil.blankToDefault(name, "请在该接口类上指定所属模块"));
        }
    }

    /**
     * 是否要记录日志
     *
     * @param handler 处理器
     * @param request 请求对象
     * @return true：需要记录；false：不需要记录
     */
    private boolean isRequestRecord(Object handler, HttpServletRequest request) {
        if (!(handler instanceof HandlerMethod handlerMethod)) {
            return false;
        }
        // 不拦截 /error
        ServerProperties serverProperties = SpringUtil.getBean(ServerProperties.class);
        if (request.getRequestURI().equals(serverProperties.getError().getPath())) {
            return false;
        }
        // 如果接口被隐藏，不记录日志
        Operation methodOperation = handlerMethod.getMethodAnnotation(Operation.class);
        if (null != methodOperation && methodOperation.hidden()) {
            return false;
        }
        Hidden methodHidden = handlerMethod.getMethodAnnotation(Hidden.class);
        if (null != methodHidden) {
            return false;
        }
        Class<?> handlerBeanType = handlerMethod.getBeanType();
        if (null != handlerBeanType.getDeclaredAnnotation(Hidden.class)) {
            return false;
        }
        // 如果接口方法或类上有 @Log 注解，且要求忽略该接口，则不记录日志
        Log methodLog = handlerMethod.getMethodAnnotation(Log.class);
        if (null != methodLog && methodLog.ignore()) {
            return false;
        }
        Log classLog = handlerBeanType.getDeclaredAnnotation(Log.class);
        return null == classLog || !classLog.ignore();
    }
}
