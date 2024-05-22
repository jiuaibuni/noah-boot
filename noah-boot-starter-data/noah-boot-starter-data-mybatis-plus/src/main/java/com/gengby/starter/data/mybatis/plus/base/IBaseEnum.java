package com.gengby.starter.data.mybatis.plus.base;

import com.baomidou.mybatisplus.annotation.IEnum;

import java.io.Serializable;

/**
 * 枚举接口
 *
 * @param <T> value 类型
 * @author Noah
 * @since 1.0.0
 */
public interface IBaseEnum<T extends Serializable> extends IEnum<T> {

    /**
     * 枚举描述
     *
     * @return 枚举描述
     */
    String getDescription();

    /**
     * 颜色
     *
     * @return 颜色
     */
    default String getColor() {
        return null;
    }
}
