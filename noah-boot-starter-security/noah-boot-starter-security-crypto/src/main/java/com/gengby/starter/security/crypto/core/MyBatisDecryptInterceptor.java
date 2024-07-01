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

package com.gengby.starter.security.crypto.core;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.ReflectUtil;
import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.type.SimpleTypeRegistry;
import com.gengby.starter.security.crypto.annotation.FieldEncrypt;
import com.gengby.starter.security.crypto.autoconfigure.CryptoProperties;
import com.gengby.starter.security.crypto.encryptor.IEncryptor;

import java.lang.reflect.Field;
import java.sql.Statement;
import java.util.List;

/**
 * 字段解密拦截器
 *
 * @author Noah
 * @since 1.0.0
 */
@Intercepts({@Signature(type = ResultSetHandler.class, method = "handleResultSets", args = {Statement.class})})
public class MyBatisDecryptInterceptor extends AbstractMyBatisInterceptor {

    private CryptoProperties properties;

    public MyBatisDecryptInterceptor(CryptoProperties properties) {
        this.properties = properties;
    }

    public MyBatisDecryptInterceptor() {
    }

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object obj = invocation.proceed();
        if (null == obj || !(invocation.getTarget() instanceof ResultSetHandler)) {
            return obj;
        }
        List<?> resultList = (List<?>)obj;
        for (Object result : resultList) {
            // String、Integer、Long 等简单类型对象无需处理
            if (SimpleTypeRegistry.isSimpleType(result.getClass())) {
                continue;
            }
            // 获取所有字符串类型、需要解密的、有值字段
            List<Field> fieldList = super.getEncryptFields(result);
            // 解密处理
            for (Field field : fieldList) {
                IEncryptor encryptor = super.getEncryptor(field.getAnnotation(FieldEncrypt.class));
                Object fieldValue = ReflectUtil.getFieldValue(result, field);
                // 优先获取自定义对称加密算法密钥，获取不到时再获取全局配置
                String password = ObjectUtil.defaultIfBlank(field.getAnnotation(FieldEncrypt.class)
                    .password(), properties.getPassword());
                String ciphertext = encryptor.decrypt(fieldValue.toString(), password, properties.getPrivateKey());
                ReflectUtil.setFieldValue(result, field, ciphertext);
            }
        }
        return resultList;
    }
}