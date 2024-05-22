package com.gengby.starter.web.autoconfigure.exception;

import jakarta.annotation.PostConstruct;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.hibernate.validator.HibernateValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.validation.beanvalidation.SpringConstraintValidatorFactory;

/**
 * 全局异常处理器自动配置
 *
 * @author Noah
 * @since 1.0.0
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnMissingBean(BasicErrorController.class)
@Import({GlobalExceptionHandler.class, GlobalErrorHandler.class})
public class GlobalExceptionHandlerAutoConfiguration {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandlerAutoConfiguration.class);

    /**
     * Validator 失败立即返回模式配置
     *
     * <p>
     * 默认情况下会校验完所有字段，然后才抛出异常。
     * </p>
     */
    @Bean
    public Validator validator(AutowireCapableBeanFactory beanFactory) {
        ValidatorFactory validatorFactory = Validation.byProvider(HibernateValidator.class)
            .configure()
            .failFast(true)
            .constraintValidatorFactory(new SpringConstraintValidatorFactory(beanFactory))
            .buildValidatorFactory();
        try (validatorFactory) {
            return validatorFactory.getValidator();
        }
    }

    @PostConstruct
    public void postConstruct() {
        log.debug("[Noah Boot] - Auto Configuration 'Web-Global Exception Handler' completed initialization.");
    }
}
