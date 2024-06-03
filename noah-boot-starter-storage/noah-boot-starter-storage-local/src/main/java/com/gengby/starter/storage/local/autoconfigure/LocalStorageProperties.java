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
