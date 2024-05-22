package com.gengby.starter.extension.crud.annotation;

import org.springframework.context.annotation.Import;
import com.gengby.starter.extension.crud.autoconfigure.CrudRestControllerAutoConfiguration;

import java.lang.annotation.*;

/**
 * CRUD REST Controller 启用注解
 *
 * @author Noah
 * @since 1.2.0
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({CrudRestControllerAutoConfiguration.class})
public @interface EnableCrudRestController {
}
