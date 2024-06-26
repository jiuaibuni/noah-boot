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

package com.gengby.starter.data.mybatis.plus.autoconfigure.idgenerator;

import cn.hutool.core.net.NetUtil;
import com.baomidou.mybatisplus.core.incrementer.DefaultIdentifierGenerator;
import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import me.ahoo.cosid.IdGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ResolvableType;

/**
 * MyBatis ID 生成器配置
 *
 * @author Noah
 * @since 1.0.0
 */
public class MyBatisPlusIdGeneratorConfiguration {

    private static final Logger log = LoggerFactory.getLogger(MyBatisPlusIdGeneratorConfiguration.class);

    private MyBatisPlusIdGeneratorConfiguration() {
    }

    /**
     * 自定义 ID 生成器-默认（雪花算法，使用网卡信息绑定雪花生成器，防止集群雪花 ID 重复）
     */
    @ConditionalOnMissingBean(IdentifierGenerator.class)
    @ConditionalOnProperty(name = "mybatis-plus.extension.id-generator.type", havingValue = "default", matchIfMissing = true)
    public static class Default {
        static {
            log.debug("[Noah Boot] - Auto Configuration 'MyBatis Plus-IdGenerator-Default' completed initialization.");
        }

        @Bean
        public IdentifierGenerator identifierGenerator() {
            return new DefaultIdentifierGenerator(NetUtil.getLocalhost());
        }
    }

    /**
     * 自定义 ID 生成器-CosId
     */
    @ConditionalOnMissingBean(IdentifierGenerator.class)
    @ConditionalOnClass(IdGenerator.class)
    @ConditionalOnProperty(name = "mybatis-plus.extension.id-generator.type", havingValue = "cosid")
    public static class CosId {
        static {
            log.debug("[Noah Boot] - Auto Configuration 'MyBatis Plus-IdGenerator-CosId' completed initialization.");
        }

        @Bean
        public IdentifierGenerator identifierGenerator() {
            return new MyBatisPlusCosIdIdentifierGenerator();
        }
    }

    /**
     * 自定义 ID 生成器
     */
    @ConditionalOnProperty(name = "mybatis-plus.extension.id-generator.type", havingValue = "custom")
    public static class Custom {
        @Bean
        @ConditionalOnMissingBean
        public IdentifierGenerator identifierGenerator() {
            if (log.isErrorEnabled()) {
                log.error("Consider defining a bean of type '{}' in your configuration.", ResolvableType
                    .forClass(IdentifierGenerator.class));
            }
            throw new NoSuchBeanDefinitionException(IdentifierGenerator.class);
        }
    }
}