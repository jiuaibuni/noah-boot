package com.gengby.starter.cache.redisson.autoconfigure;

import org.redisson.config.ClusterServersConfig;
import org.redisson.config.SentinelServersConfig;
import org.redisson.config.SingleServerConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Redisson 配置属性
 *
 * @author gengwei.zheng（<a href="https://gitee.com/herodotus/dante-engine">Dante Engine</a>）
 * @author Noah
 * @since 1.0.0
 */
@ConfigurationProperties(prefix = "spring.data.redisson")
public class RedissonProperties {

    /**
     * 是否启用 Redisson
     */
    private boolean enabled = false;

    /**
     * Redis 模式
     */
    private Mode mode = Mode.SINGLE;

    /**
     * 单机服务配置
     */
    private SingleServerConfig singleServerConfig;

    /**
     * 集群服务配置
     */
    private ClusterServersConfig clusterServersConfig;

    /**
     * 哨兵服务配置
     */
    private SentinelServersConfig sentinelServersConfig;

    /**
     * Redis 模式
     */
    public enum Mode {
        /**
         * 单机
         */
        SINGLE,

        /**
         * 集群
         */
        CLUSTER,

        /**
         * 哨兵
         */
        SENTINEL
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Mode getMode() {
        return mode;
    }

    public void setMode(Mode mode) {
        this.mode = mode;
    }

    public SingleServerConfig getSingleServerConfig() {
        return singleServerConfig;
    }

    public void setSingleServerConfig(SingleServerConfig singleServerConfig) {
        this.singleServerConfig = singleServerConfig;
    }

    public ClusterServersConfig getClusterServersConfig() {
        return clusterServersConfig;
    }

    public void setClusterServersConfig(ClusterServersConfig clusterServersConfig) {
        this.clusterServersConfig = clusterServersConfig;
    }

    public SentinelServersConfig getSentinelServersConfig() {
        return sentinelServersConfig;
    }

    public void setSentinelServersConfig(SentinelServersConfig sentinelServersConfig) {
        this.sentinelServersConfig = sentinelServersConfig;
    }
}
