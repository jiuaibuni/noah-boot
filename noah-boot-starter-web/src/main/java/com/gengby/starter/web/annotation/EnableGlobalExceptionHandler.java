package com.gengby.starter.web.annotation;

import com.gengby.starter.web.autoconfigure.exception.GlobalExceptionHandlerAutoConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 全局异常、错误处理器启用注解
 *
 * @author Noah
 * @since 1.0.0
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({GlobalExceptionHandlerAutoConfiguration.class})
public @interface EnableGlobalExceptionHandler {
}
