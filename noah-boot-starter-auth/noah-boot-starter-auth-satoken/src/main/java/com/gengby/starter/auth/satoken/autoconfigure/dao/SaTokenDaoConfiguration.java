package com.gengby.starter.auth.satoken.autoconfigure.dao;

import cn.dev33.satoken.dao.SaTokenDao;
import cn.dev33.satoken.dao.SaTokenDaoDefaultImpl;
import org.redisson.client.RedisClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ResolvableType;
import com.gengby.starter.cache.redisson.autoconfigure.RedissonAutoConfiguration;

/**
 * SaToken 持久层配置
 *
 * @author Noah
 * @since 1.0.0
 */
public class SaTokenDaoConfiguration {

    private static final Logger log = LoggerFactory.getLogger(SaTokenDaoConfiguration.class);

    private SaTokenDaoConfiguration() {
    }

    /**
     * 自定义持久层实现-默认（内存）
     */
    @ConditionalOnMissingBean(SaTokenDao.class)
    @ConditionalOnProperty(name = "sa-token.extension.dao.type", havingValue = "default", matchIfMissing = true)
    public static class Default {
        static {
            log.debug("[Noah Boot] - Auto Configuration 'SaToken-Dao-Default' completed initialization.");
        }

        @Bean
        public SaTokenDao saTokenDao() {
            return new SaTokenDaoDefaultImpl();
        }
    }

    /**
     * 自定义持久层实现-Redis
     */
    @ConditionalOnMissingBean(SaTokenDao.class)
    @ConditionalOnClass(RedisClient.class)
    @AutoConfigureBefore(RedissonAutoConfiguration.class)
    @ConditionalOnProperty(name = "sa-token.extension.dao.type", havingValue = "redis")
    public static class Redis {
        static {
            log.debug("[Noah Boot] - Auto Configuration 'SaToken-Dao-Redis' completed initialization.");
        }

        @Bean
        public SaTokenDao saTokenDao() {
            return new SaTokenDaoRedisImpl();
        }
    }

    /**
     * 自定义持久层实现
     */
    @ConditionalOnProperty(name = "sa-token.extension.dao.type", havingValue = "custom")
    public static class Custom {
        @Bean
        @ConditionalOnMissingBean
        public SaTokenDao saTokenDao() {
            if (log.isErrorEnabled()) {
                log.error("Consider defining a bean of type '{}' in your configuration.", ResolvableType
                    .forClass(SaTokenDao.class));
            }
            throw new NoSuchBeanDefinitionException(SaTokenDao.class);
        }
    }
}