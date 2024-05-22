package com.gengby.boot.apidoc.autoconfigure;

import com.gengby.starter.core.constant.PropertiesConstants;
import io.swagger.v3.oas.models.Components;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

/**
 * API 文档扩展配置属性
 *
 * @author Noah
 * @since 1.0.1
 */
@ConfigurationProperties(prefix = PropertiesConstants.SPRINGDOC)
public class SpringDocExtensionProperties {

    /**
     * 组件配置（包括鉴权配置等）
     */
    @NestedConfigurationProperty
    private Components components;

    public Components getComponents() {
        return components;
    }

    public void setComponents(Components components) {
        this.components = components;
    }
}
