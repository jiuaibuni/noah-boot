package com.gengby.starter.data.core.annotation;

import java.lang.annotation.*;

/**
 * 查询解析忽略注解
 *
 * @author Noah
 * @since 1.3.0
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface QueryIgnore {

    /**
     * 获取是否忽略查询解析
     *
     * @return 是否忽略查询解析
     */
    boolean value() default true;
}
