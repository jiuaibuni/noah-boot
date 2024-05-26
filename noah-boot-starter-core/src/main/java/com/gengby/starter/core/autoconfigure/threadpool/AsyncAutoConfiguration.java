package com.gengby.starter.core.autoconfigure.threadpool;

import cn.hutool.core.util.ArrayUtil;
import com.gengby.starter.core.constant.PropertiesConstants;
import com.gengby.starter.core.exception.BaseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.Arrays;
import java.util.concurrent.Executor;
import java.util.concurrent.ScheduledExecutorService;

/**
 * 异步任务自动配置
 *
 * @author Noah
 * @since 1.0.0
 */
@Lazy
@AutoConfiguration
@EnableAsync(proxyTargetClass = true)
@ConditionalOnProperty(prefix = PropertiesConstants.THREAD_POOL, name = PropertiesConstants.ENABLED, havingValue = "true")
public class AsyncAutoConfiguration implements AsyncConfigurer {
    private static final Logger log = LoggerFactory.getLogger(AsyncAutoConfiguration.class);

    private final ScheduledExecutorService scheduledExecutorService;

    public AsyncAutoConfiguration(ScheduledExecutorService scheduledExecutorService) {
        this.scheduledExecutorService = scheduledExecutorService;
    }

    /**
     * 异步任务 @Async 执行时，使用 Java 内置线程池
     */
    @Override
    public Executor getAsyncExecutor() {
        log.debug("[Noah Boot] - Auto Configuration 'AsyncConfigurer' completed initialization.");
        return scheduledExecutorService;
    }

    /**
     * 异步任务执行时的异常处理
     */
    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return (throwable, method, objects) -> {
            throwable.printStackTrace();
            StringBuilder sb = new StringBuilder();
            sb.append("Exception message: ")
                .append(throwable.getMessage())
                .append(", Method name: ")
                .append(method.getName());
            if (ArrayUtil.isNotEmpty(objects)) {
                sb.append(", Parameter value: ").append(Arrays.toString(objects));
            }
            throw new BaseException(sb.toString());
        };
    }
}
