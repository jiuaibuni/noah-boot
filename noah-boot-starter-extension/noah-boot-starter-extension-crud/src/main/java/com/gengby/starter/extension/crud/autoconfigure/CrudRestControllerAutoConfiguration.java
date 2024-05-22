package com.gengby.starter.extension.crud.autoconfigure;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.servlet.config.annotation.DelegatingWebMvcConfiguration;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.servlet.resource.ResourceUrlProvider;

/**
 * CRUD REST Controller 自动配置
 *
 * @author Noah
 * @since 1.0.0
 */
@Configuration
public class CrudRestControllerAutoConfiguration extends DelegatingWebMvcConfiguration {

    private static final Logger log = LoggerFactory.getLogger(CrudRestControllerAutoConfiguration.class);

    /**
     * CRUD 请求映射器处理器映射器（覆盖默认 RequestMappingHandlerMapping）
     */
    @Override
    public RequestMappingHandlerMapping createRequestMappingHandlerMapping() {
        return new CrudRequestMappingHandlerMapping();
    }

    @Bean
    @Primary
    @Override
    public RequestMappingHandlerMapping requestMappingHandlerMapping(@Qualifier("mvcContentNegotiationManager") ContentNegotiationManager contentNegotiationManager,
                                                                     @Qualifier("mvcConversionService") FormattingConversionService conversionService,
                                                                     @Qualifier("mvcResourceUrlProvider") ResourceUrlProvider resourceUrlProvider) {
        return super.requestMappingHandlerMapping(contentNegotiationManager, conversionService, resourceUrlProvider);
    }

    @PostConstruct
    public void postConstruct() {
        log.debug("[Noah Boot] - Auto Configuration 'Extension-CRUD REST Controller' completed initialization.");
    }
}
