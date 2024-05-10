/*
 * Copyright (c) 2022-present Charles7c Authors. All Rights Reserved.
 * <p>
 * Licensed under the GNU LESSER GENERAL PUBLIC LICENSE 3.0;
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.gnu.org/licenses/lgpl.html
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.gengby.boot.core.util;

import cn.hutool.core.text.CharSequenceUtil;
import org.springframework.boot.env.YamlPropertySourceLoader;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.DefaultPropertySourceFactory;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.lang.Nullable;

import java.io.IOException;

/**
 * 通用配置文件读取工厂
 *
 * @author Noah
 * @since 1.0.0
 */
public class GeneralPropertySourceFactory extends DefaultPropertySourceFactory {

    @Override
    public PropertySource<?> createPropertySource(@Nullable String name,
                                                  EncodedResource encodedResource) throws IOException {
        Resource resource = encodedResource.getResource();
        String resourceName = resource.getFilename();
        if (CharSequenceUtil.isNotBlank(resourceName) && CharSequenceUtil.endWithAny(resourceName, ".yml", ".yaml")) {
            return new YamlPropertySourceLoader().load(resourceName, resource).get(0);
        }
        return super.createPropertySource(name, encodedResource);
    }
}
