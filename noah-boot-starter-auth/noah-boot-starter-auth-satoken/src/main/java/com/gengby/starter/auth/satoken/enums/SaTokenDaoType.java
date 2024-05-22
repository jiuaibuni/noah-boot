package com.gengby.starter.auth.satoken.enums;

/**
 * SaToken 持久层类型枚举
 *
 * @author Noah
 * @since 1.0.0
 */
public enum SaTokenDaoType {

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
    CUSTOM
}
