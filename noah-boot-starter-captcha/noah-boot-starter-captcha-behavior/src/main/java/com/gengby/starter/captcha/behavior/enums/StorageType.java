package com.gengby.starter.captcha.behavior.enums;

/**
 * 缓存类型枚举
 *
 * @author Bull-BCLS
 * @since 1.1.0
 */
public enum StorageType {

    /**
     * 默认（内存）
     */
    DEFAULT,

    /**
     * Redis
     */
    REDIS,

    /**
     * 自定义
     */
    CUSTOM,
}
