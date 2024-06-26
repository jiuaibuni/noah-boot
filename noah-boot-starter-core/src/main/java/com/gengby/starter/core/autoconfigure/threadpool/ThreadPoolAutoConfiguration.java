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

package com.gengby.starter.core.autoconfigure.threadpool;

import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.ObjectUtil;
import com.gengby.starter.core.constant.PropertiesConstants;
import com.gengby.starter.core.util.ExceptionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.*;

/**
 * 线程池自动配置
 *
 * @author Noah
 * @since 1.0.0
 */
@Lazy
@AutoConfiguration
@ConditionalOnProperty(prefix = PropertiesConstants.THREAD_POOL, name = PropertiesConstants.ENABLED, havingValue = "true")
@EnableConfigurationProperties(ThreadPoolProperties.class)
public class ThreadPoolAutoConfiguration {
    private static final Logger log = LoggerFactory.getLogger(ThreadPoolAutoConfiguration.class);

    /**
     * 核心（最小）线程数 = CPU 核心数 + 1
     */
    private final int corePoolSize = Runtime.getRuntime().availableProcessors() + 1;

    /**
     * Spring 内置线程池：ThreadPoolTaskExecutor
     */
    @Bean
    public ThreadPoolTaskExecutor threadPoolTaskExecutor(ThreadPoolProperties properties) {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        // 核心（最小）线程数
        executor.setCorePoolSize(ObjectUtil.defaultIfNull(properties.getCorePoolSize(), corePoolSize));
        // 最大线程数
        executor.setMaxPoolSize(ObjectUtil.defaultIfNull(properties.getMaxPoolSize(), corePoolSize * 2));
        // 队列容量
        executor.setQueueCapacity(properties.getQueueCapacity());
        // 活跃时间
        executor.setKeepAliveSeconds(properties.getKeepAliveSeconds());
        // 配置当池内线程数已达到上限的时候，该如何处理新任务：不在新线程中执行任务，而是由调用者所在的线程来执行
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        // 关闭线程池是否等待任务完成
        executor.setWaitForTasksToCompleteOnShutdown(properties.isWaitForTasksToCompleteOnShutdown());
        // 执行器在关闭时阻塞的最长毫秒数，以等待剩余任务完成执行
        executor.setAwaitTerminationMillis(properties.getAwaitTerminationMillis());
        log.debug("[Noah Boot] - Auto Configuration 'ThreadPoolTaskExecutor' completed initialization.");
        return executor;
    }

    /**
     * Java 内置线程池：ScheduledExecutorService（适用于执行周期性或定时任务）
     */
    @Bean
    @ConditionalOnMissingBean
    public ScheduledExecutorService scheduledExecutorService(ThreadPoolProperties properties) {
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(ObjectUtil.defaultIfNull(properties
            .getCorePoolSize(), corePoolSize), ThreadUtil
                .newNamedThreadFactory("schedule-pool-%d", true), new ThreadPoolExecutor.CallerRunsPolicy()) {
            @Override
            protected void afterExecute(Runnable runnable, Throwable throwable) {
                super.afterExecute(runnable, throwable);
                ExceptionUtil.printException(runnable, throwable);
            }
        };
        // 应用关闭时，关闭线程池
        SpringApplication.getShutdownHandlers().add(() -> shutdown(executor, properties));
        log.debug("[Noah Boot] - Auto Configuration 'ScheduledExecutorService' completed initialization.");
        return executor;
    }

    /**
     * 根据相应的配置设置关闭 ExecutorService
     *
     * @see org.springframework.scheduling.concurrent.ExecutorConfigurationSupport#shutdown()
     */
    public void shutdown(ExecutorService executor, ThreadPoolProperties properties) {
        log.debug("[Noah Boot] - Shutting down ScheduledExecutorService start.");
        if (executor != null) {
            if (properties.isWaitForTasksToCompleteOnShutdown()) {
                executor.shutdown();
            } else {
                for (Runnable remainingTask : executor.shutdownNow()) {
                    cancelRemainingTask(remainingTask);
                }
            }
            awaitTerminationIfNecessary(executor, properties);
            log.debug("[Noah Boot] - Shutting down ScheduledExecutorService complete.");
        }
    }

    /**
     * Cancel the given remaining task which never commenced execution,
     * as returned from {@link ExecutorService#shutdownNow()}.
     *
     * @param task the task to cancel (typically a {@link RunnableFuture})
     * @see RunnableFuture#cancel(boolean)
     */
    protected void cancelRemainingTask(Runnable task) {
        if (task instanceof Future<?> future) {
            future.cancel(true);
        }
    }

    /**
     * Wait for the executor to terminate, according to the value of the properties
     */
    private void awaitTerminationIfNecessary(ExecutorService executor, ThreadPoolProperties properties) {
        if (properties.getAwaitTerminationMillis() > 0) {
            try {
                if (!executor.awaitTermination(properties.getAwaitTerminationMillis(), TimeUnit.MILLISECONDS)) {
                    if (log.isWarnEnabled()) {
                        log.warn("[Noah Boot] - Timed out while waiting for  executor 'ScheduledExecutorService' to terminate.");
                    }
                }
            } catch (InterruptedException ex) {
                if (log.isWarnEnabled()) {
                    log.warn("[Noah Boot] - Interrupted while waiting for executor 'ScheduledExecutorService' to terminate");
                }
                Thread.currentThread().interrupt();
            }
        }
    }
}
