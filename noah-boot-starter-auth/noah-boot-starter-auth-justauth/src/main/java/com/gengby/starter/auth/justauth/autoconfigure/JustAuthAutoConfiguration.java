package com.gengby.starter.auth.justauth.autoconfigure;

import jakarta.annotation.PostConstruct;
import me.zhyd.oauth.cache.AuthStateCache;
import org.redisson.client.RedisClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import com.gengby.starter.auth.justauth.core.JustAuthStateCacheRedisImpl;
import com.gengby.starter.core.constant.PropertiesConstants;

/**
 * JustAuth 自动配置
 *
 * @author Noah
 * @since 1.0.0
 */
@AutoConfiguration(before = com.xkcoding.justauth.autoconfigure.JustAuthAutoConfiguration.class)
@ConditionalOnProperty(prefix = "justauth", name = PropertiesConstants.ENABLED, havingValue = "true", matchIfMissing = true)
public class JustAuthAutoConfiguration {

    private static final Logger log = LoggerFactory.getLogger(JustAuthAutoConfiguration.class);

    /**
     * 自定义 State 缓存实现
     */
    @Bean
    @ConditionalOnClass(RedisClient.class)
    @ConditionalOnProperty(prefix = "justauth.cache", name = "type", havingValue = "redis")
    public AuthStateCache authStateCache() {
        JustAuthStateCacheRedisImpl impl = new JustAuthStateCacheRedisImpl();
        log.debug("[Noah Boot] - Auto Configuration 'JustAuth-AuthStateCache-Redis' completed initialization.");
        return impl;
    }

    @PostConstruct
    public void postConstruct() {
        log.debug("[Noah Boot] - Auto Configuration 'JustAuth' completed initialization.");
    }
}