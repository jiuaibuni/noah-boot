package com.gengby.starter.storage.local.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.util.unit.DataSize;
import com.gengby.starter.core.constant.PropertiesConstants;

import java.util.HashMap;
import java.util.Map;

/**
 * 本地存储配置属性
 *
 * @author Noah
 * @since 1.1.0
 */
@ConfigurationProperties(PropertiesConstants.STORAGE_LOCAL)
public class LocalStorageProperties {

    /**
     * 是否启用本地存储
     */
    private boolean enabled = false;

    /**
     * 存储映射
     */
    private Map<String, LocalStorageMapping> mapping = new HashMap<>();

    /**
     * 本地存储映射
     */
    public static class LocalStorageMapping {

        /**
         * 路径模式
         */
        private String pathPattern;

        /**
         * 资源路径
         */
        private String location;

        /**
         * 单文件上传大小限制
         */
        private DataSize maxFileSize = DataSize.ofMegabytes(1);

        public String getPathPattern() {
            return pathPattern;
        }

        public void setPathPattern(String pathPattern) {
            this.pathPattern = pathPattern;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public DataSize getMaxFileSize() {
            return maxFileSize;
        }

        public void setMaxFileSize(DataSize maxFileSize) {
            this.maxFileSize = maxFileSize;
        }
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Map<String, LocalStorageMapping> getMapping() {
        return mapping;
    }

    public void setMapping(Map<String, LocalStorageMapping> mapping) {
        this.mapping = mapping;
    }
}
