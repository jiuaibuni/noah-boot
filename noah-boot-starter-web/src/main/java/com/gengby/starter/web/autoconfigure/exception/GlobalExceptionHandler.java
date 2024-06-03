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

package com.gengby.starter.web.autoconfigure.exception;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.NumberUtil;
import com.gengby.starter.core.constant.StringConstants;
import com.gengby.starter.core.exception.BadRequestException;
import com.gengby.starter.core.exception.BusinessException;
import com.gengby.starter.web.model.R;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

/**
 * 全局异常处理器
 *
 * @author Noah
 * @since 1.0.0
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    private static final String PARAM_FAILED = "请求地址 [{}]，参数验证失败。";

    /**
     * 拦截自定义验证异常-错误请求
     */
    @ExceptionHandler(BadRequestException.class)
    public R<Void> handleBadRequestException(BadRequestException e, HttpServletRequest request) {
        log.warn("请求地址 [{}]，自定义验证失败。", request.getRequestURI(), e);
        return R.fail(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }

    /**
     * 拦截校验异常-违反约束异常
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public R<Void> constraintViolationException(ConstraintViolationException e, HttpServletRequest request) {
        log.warn(PARAM_FAILED, request.getRequestURI(), e);
        String errorMsg = CollUtil.join(e
            .getConstraintViolations(), StringConstants.CHINESE_COMMA, ConstraintViolation::getMessage);
        return R.fail(HttpStatus.BAD_REQUEST.value(), errorMsg);
    }

    /**
     * 拦截校验异常-绑定异常
     */
    @ExceptionHandler(BindException.class)
    public R<Void> handleBindException(BindException e, HttpServletRequest request) {
        log.warn(PARAM_FAILED, request.getRequestURI(), e);
        String errorMsg = CollUtil.join(e
            .getAllErrors(), StringConstants.CHINESE_COMMA, DefaultMessageSourceResolvable::getDefaultMessage);
        return R.fail(HttpStatus.BAD_REQUEST.value(), errorMsg);
    }

    /**
     * 拦截校验异常-方法参数无效异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public R<Void> handleMethodArgumentNotValidException(MethodArgumentNotValidException e,
                                                         HttpServletRequest request) {
        log.warn(PARAM_FAILED, request.getRequestURI(), e);
        String errorMsg = CollUtil.join(e
            .getAllErrors(), StringConstants.CHINESE_COMMA, DefaultMessageSourceResolvable::getDefaultMessage);
        return R.fail(HttpStatus.BAD_REQUEST.value(), errorMsg);
    }

    /**
     * 拦截校验异常-方法参数类型不匹配异常
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public R<Void> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e,
                                                             HttpServletRequest request) {
        String errorMsg = CharSequenceUtil.format("参数名：[{}]，期望参数类型：[{}]", e.getName(), e.getParameter()
            .getParameterType());
        log.warn("请求地址 [{}]，参数转换失败，{}。", request.getRequestURI(), errorMsg, e);
        return R.fail(HttpStatus.BAD_REQUEST.value(), errorMsg);
    }

    /**
     * 拦截文件上传异常-超过上传大小限制
     */
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public R<Void> handleMaxUploadSizeExceededException(MaxUploadSizeExceededException e, HttpServletRequest request) {
        log.warn("请求地址 [{}]，上传文件失败，文件大小超过限制。", request.getRequestURI(), e);
        String sizeLimit = CharSequenceUtil.subBetween(e.getMessage(), "The maximum size ", " for");
        String errorMsg = "请上传小于 %sMB 的文件".formatted(NumberUtil.parseLong(sizeLimit) / 1024 / 1024);
        return R.fail(HttpStatus.BAD_REQUEST.value(), errorMsg);
    }

    /**
     * 拦截校验异常-请求方式不支持异常
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public R<Void> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException e,
                                                       HttpServletRequest request) {
        log.error("请求地址 [{}]，不支持 [{}] 请求。", request.getRequestURI(), e.getMethod());
        return R.fail(HttpStatus.METHOD_NOT_ALLOWED.value(), e.getMessage());
    }

    /**
     * 拦截业务异常
     */
    @ExceptionHandler(BusinessException.class)
    public R<Void> handleServiceException(BusinessException e, HttpServletRequest request) {
        log.error("请求地址 [{}]，发生业务异常。", request.getRequestURI(), e);
        return R.fail(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
    }

    /**
     * 拦截未知的运行时异常
     */
    @ExceptionHandler(RuntimeException.class)
    public R<Void> handleRuntimeException(RuntimeException e, HttpServletRequest request) {
        log.error("请求地址 [{}]，发生系统异常。", request.getRequestURI(), e);
        return R.fail(e.getMessage());
    }

    /**
     * 拦截未知的系统异常
     */
    @ExceptionHandler(Throwable.class)
    public R<Void> handleException(Throwable e, HttpServletRequest request) {
        log.error("请求地址 [{}]，发生未知异常。", request.getRequestURI(), e);
        return R.fail(e.getMessage());
    }
}