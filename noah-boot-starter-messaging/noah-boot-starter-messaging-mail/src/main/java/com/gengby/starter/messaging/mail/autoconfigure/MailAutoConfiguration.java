package com.gengby.starter.messaging.mail.autoconfigure;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.PropertySource;
import com.gengby.starter.core.util.GeneralPropertySourceFactory;

/**
 * 邮件自动配置
 *
 * @author Noah
 * @since 1.0.0
 */
@AutoConfiguration
@PropertySource(value = "classpath:default-messaging-mail.yml", factory = GeneralPropertySourceFactory.class)
public class MailAutoConfiguration {

    private static final Logger log = LoggerFactory.getLogger(MailAutoConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.debug("[Noah Boot] - Auto Configuration 'Mail' completed initialization.");
    }
}
