package com.gengby.starter.auth.satoken.autoconfigure.dao;

import com.gengby.starter.auth.satoken.enums.SaTokenDaoType;

/**
 * SaToken 持久层配置属性
 *
 * @author Noah
 * @since 1.0.0
 */
public class SaTokenDaoProperties {

    /**
     * 持久层类型
     */
    private SaTokenDaoType type = SaTokenDaoType.DEFAULT;

    public SaTokenDaoType getType() {
        return type;
    }

    public void setType(SaTokenDaoType type) {
        this.type = type;
    }
}
