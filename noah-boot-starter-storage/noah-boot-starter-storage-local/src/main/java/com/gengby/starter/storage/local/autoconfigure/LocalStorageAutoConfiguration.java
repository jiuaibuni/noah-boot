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

package com.gengby.starter.storage.local.autoconfigure;

import cn.hutool.core.text.CharSequenceUtil;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import com.gengby.starter.core.constant.PropertiesConstants;
import com.gengby.starter.core.constant.StringConstants;

import java.util.Map;

/**
 * 本地文件自动配置
 *
 * @author Noah
 * @since 1.0.0
 */
@EnableWebMvc
@AutoConfiguration
@EnableConfigurationProperties(LocalStorageProperties.class)
@ConditionalOnProperty(prefix = PropertiesConstants.STORAGE_LOCAL, name = PropertiesConstants.ENABLED, havingValue = "true")
public class LocalStorageAutoConfiguration implements WebMvcConfigurer {

    private static final Logger log = LoggerFactory.getLogger(LocalStorageAutoConfiguration.class);
    private final LocalStorageProperties properties;

    public LocalStorageAutoConfiguration(LocalStorageProperties properties) {
        this.properties = properties;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        Map<String, LocalStorageProperties.LocalStorageMapping> mappingMap = properties.getMapping();
        for (Map.Entry<String, LocalStorageProperties.LocalStorageMapping> mappingEntry : mappingMap.entrySet()) {
            LocalStorageProperties.LocalStorageMapping mapping = mappingEntry.getValue();
            String pathPattern = mapping.getPathPattern();
            String location = mapping.getLocation();
            if (CharSequenceUtil.isBlank(location)) {
                throw new IllegalArgumentException("Path pattern [%s] location is null.".formatted(pathPattern));
            }
            registry.addResourceHandler(CharSequenceUtil.appendIfMissing(pathPattern, StringConstants.PATH_PATTERN))
                .addResourceLocations(!location.startsWith("file:")
                    ? "file:%s".formatted(this.format(location))
                    : this.format(location))
                .setCachePeriod(0);
        }
    }

    private String format(String location) {
        return location.replace(StringConstants.BACKSLASH, StringConstants.SLASH);
    }

    @PostConstruct
    public void postConstruct() {
        log.debug("[Noah Boot] - Auto Configuration 'Storage-Local' completed initialization.");
    }
}
