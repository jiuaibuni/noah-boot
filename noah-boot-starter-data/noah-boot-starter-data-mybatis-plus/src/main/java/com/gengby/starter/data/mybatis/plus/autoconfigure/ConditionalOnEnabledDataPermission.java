package com.gengby.starter.data.mybatis.plus.autoconfigure;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import com.gengby.starter.core.constant.PropertiesConstants;

import java.lang.annotation.*;

/**
 * 是否启用数据权限注解
 *
 * @author Noah
 * @since 1.1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Documented
@ConditionalOnProperty(prefix = "mybatis-plus.extension.data-permission", name = PropertiesConstants.ENABLED, havingValue = "true")
public @interface ConditionalOnEnabledDataPermission {
}