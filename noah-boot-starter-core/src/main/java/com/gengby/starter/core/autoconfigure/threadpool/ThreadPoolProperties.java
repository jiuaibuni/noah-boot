package com.gengby.starter.core.autoconfigure.threadpool;

import com.gengby.starter.core.constant.PropertiesConstants;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 线程池配置属性
 *
 * @author Noah
 * @since 1.0.0
 */
@ConfigurationProperties(PropertiesConstants.THREAD_POOL)
public class ThreadPoolProperties {

    /**
     * 是否启用线程池配置
     */
    private boolean enabled = false;

    /**
     * 核心/最小线程数（默认：CPU 核心数 + 1）
     */
    private Integer corePoolSize;

    /**
     * 最大线程数（默认：(CPU 核心数 + 1) * 2）
     */
    private Integer maxPoolSize;

    /**
     * 队列容量
     */
    private int queueCapacity = 128;

    /**
     * 活跃时间（单位：秒）
     */
    private int keepAliveSeconds = 300;

    /**
     * 关闭线程池是否等待任务完成
     */
    private boolean waitForTasksToCompleteOnShutdown = false;

    /**
     * 执行器在关闭时阻塞的最长毫秒数，以等待剩余任务完成执行
     */
    private long awaitTerminationMillis = 0;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Integer getCorePoolSize() {
        return corePoolSize;
    }

    public void setCorePoolSize(Integer corePoolSize) {
        this.corePoolSize = corePoolSize;
    }

    public Integer getMaxPoolSize() {
        return maxPoolSize;
    }

    public void setMaxPoolSize(Integer maxPoolSize) {
        this.maxPoolSize = maxPoolSize;
    }

    public int getQueueCapacity() {
        return queueCapacity;
    }

    public void setQueueCapacity(int queueCapacity) {
        this.queueCapacity = queueCapacity;
    }

    public int getKeepAliveSeconds() {
        return keepAliveSeconds;
    }

    public void setKeepAliveSeconds(int keepAliveSeconds) {
        this.keepAliveSeconds = keepAliveSeconds;
    }

    public boolean isWaitForTasksToCompleteOnShutdown() {
        return waitForTasksToCompleteOnShutdown;
    }

    public void setWaitForTasksToCompleteOnShutdown(boolean waitForTasksToCompleteOnShutdown) {
        this.waitForTasksToCompleteOnShutdown = waitForTasksToCompleteOnShutdown;
    }

    public long getAwaitTerminationMillis() {
        return awaitTerminationMillis;
    }

    public void setAwaitTerminationMillis(long awaitTerminationMillis) {
        this.awaitTerminationMillis = awaitTerminationMillis;
    }
}
