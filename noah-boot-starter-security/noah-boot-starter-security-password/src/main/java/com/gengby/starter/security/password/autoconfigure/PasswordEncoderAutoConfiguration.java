/*
 * MIT License
 *
 *  Copyright (c) 2024 久爱不腻gby
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in all
 *  copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *  SOFTWARE.
 */

package com.gengby.starter.security.password.autoconfigure;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.text.CharSequenceUtil;
import com.gengby.starter.core.util.validate.CheckUtil;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.*;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import com.gengby.starter.core.constant.PropertiesConstants;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 密码编码器自动配置
 *
 * <p>
 * 密码配置类，默认编解码器使用的是 BCryptPasswordEncoder <br />
 * 编码后的密码是遵循一定规则的 {idForEncode}encodePassword，前缀 {} 包含了编码的方式再拼接上该方式编码后的密码串。<br />
 * 可以添加自定义的编解码，也可以修改默认的编解码器，只需修改默认的 encodingId。<br />
 * 优点：如果有一天我们对密码编码规则进行替换或者轮转，现有的用户不会受到影响，只要修改 DelegatingPasswordEncoder 的 idForEncode 即可。
 * </p>
 *
 * @author Noah
 * @since 1.3.0
 */
@AutoConfiguration
@EnableConfigurationProperties(PasswordEncoderProperties.class)
@ConditionalOnProperty(prefix = PropertiesConstants.PASSWORD, name = PropertiesConstants.ENABLED, matchIfMissing = true)
public class PasswordEncoderAutoConfiguration {

    private static final Logger log = LoggerFactory.getLogger(PasswordEncoderAutoConfiguration.class);
    private final PasswordEncoderProperties properties;

    public PasswordEncoderAutoConfiguration(PasswordEncoderProperties properties) {
        this.properties = properties;
    }

    /**
     * 密码编码器
     *
     * @see DelegatingPasswordEncoder
     * @see PasswordEncoderFactories
     */
    @Bean
    public PasswordEncoder passwordEncoder(List<PasswordEncoder> passwordEncoderList) {
        Map<String, PasswordEncoder> encoders = new HashMap<>();
        encoders.put("bcrypt", new BCryptPasswordEncoder());
        encoders.put("pbkdf2", Pbkdf2PasswordEncoder.defaultsForSpringSecurity_v5_8());
        encoders.put("scrypt", SCryptPasswordEncoder.defaultsForSpringSecurity_v5_8());
        encoders.put("argon2", Argon2PasswordEncoder.defaultsForSpringSecurity_v5_8());
        // 添加自定义的密码编解码器
        if (CollUtil.isNotEmpty(passwordEncoderList)) {
            passwordEncoderList.forEach(passwordEncoder -> {
                String simpleName = passwordEncoder.getClass().getSimpleName();
                encoders.put(CharSequenceUtil.removeSuffix(simpleName, "PasswordEncoder")
                    .toLowerCase(), passwordEncoder);
            });
        }
        String encodingId = properties.getEncodingId();
        CheckUtil.throwIf(!encoders.containsKey(encodingId), "{} is not found in idToPasswordEncoder.", encodingId);
        return new DelegatingPasswordEncoder(encodingId, encoders);
    }

    @PostConstruct
    public void postConstruct() {
        log.debug("[Noah Boot] - Auto Configuration 'Security-PasswordEncoder' completed initialization.");
    }
}
