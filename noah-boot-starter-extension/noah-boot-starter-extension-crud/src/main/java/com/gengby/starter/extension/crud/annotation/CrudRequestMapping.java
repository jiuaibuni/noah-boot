package com.gengby.starter.extension.crud.annotation;

import com.gengby.starter.extension.crud.enums.Api;

import java.lang.annotation.*;

/**
 * CRUD（增删改查）请求映射器注解
 *
 * @author Noah
 * @since 1.0.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CrudRequestMapping {

    /**
     * 路径映射 URI（等同于：@RequestMapping("/foo1")）
     */
    String value() default "";

    /**
     * API 列表
     */
    Api[] api() default {Api.PAGE, Api.GET, Api.ADD, Api.UPDATE, Api.DELETE, Api.EXPORT};
}
