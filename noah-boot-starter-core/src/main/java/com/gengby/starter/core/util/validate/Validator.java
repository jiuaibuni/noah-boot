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

package com.gengby.starter.core.util.validate;

import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.ReflectUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.BooleanSupplier;

/**
 * 校验器
 *
 * @author Noah
 * @since 1.0.0
 */
public class Validator {
    private static final Logger log = LoggerFactory.getLogger(Validator.class);

    protected Validator() {
    }

    /**
     * 如果为空，抛出异常
     *
     * @param obj           被检测的对象
     * @param message       错误信息
     * @param exceptionType 异常类型
     */
    protected static void throwIfNull(Object obj, String message, Class<? extends RuntimeException> exceptionType) {
        throwIf(null == obj, message, exceptionType);
    }

    /**
     * 如果不为空，抛出异常
     *
     * @param obj           被检测的对象
     * @param message       错误信息
     * @param exceptionType 异常类型
     */
    protected static void throwIfNotNull(Object obj, String message, Class<? extends RuntimeException> exceptionType) {
        throwIf(null != obj, message, exceptionType);
    }

    /**
     * 如果为空，抛出异常
     *
     * @param obj           被检测的对象
     * @param message       错误信息
     * @param exceptionType 异常类型
     */
    protected static void throwIfEmpty(Object obj, String message, Class<? extends RuntimeException> exceptionType) {
        throwIf(ObjectUtil.isEmpty(obj), message, exceptionType);
    }

    /**
     * 如果不为空，抛出异常
     *
     * @param obj           被检测的对象
     * @param message       错误信息
     * @param exceptionType 异常类型
     */
    protected static void throwIfNotEmpty(Object obj, String message, Class<? extends RuntimeException> exceptionType) {
        throwIf(ObjectUtil.isNotEmpty(obj), message, exceptionType);
    }

    /**
     * 如果为空，抛出异常
     *
     * @param str           被检测的字符串
     * @param message       错误信息
     * @param exceptionType 异常类型
     */
    protected static void throwIfBlank(CharSequence str,
                                       String message,
                                       Class<? extends RuntimeException> exceptionType) {
        throwIf(CharSequenceUtil.isBlank(str), message, exceptionType);
    }

    /**
     * 如果不为空，抛出异常
     *
     * @param str           被检测的字符串
     * @param message       错误信息
     * @param exceptionType 异常类型
     */
    protected static void throwIfNotBlank(CharSequence str,
                                          String message,
                                          Class<? extends RuntimeException> exceptionType) {
        throwIf(CharSequenceUtil.isNotBlank(str), message, exceptionType);
    }

    /**
     * 如果相同，抛出异常
     *
     * @param obj1          要比较的对象1
     * @param obj2          要比较的对象2
     * @param message       错误信息
     * @param exceptionType 异常类型
     */
    protected static void throwIfEqual(Object obj1,
                                       Object obj2,
                                       String message,
                                       Class<? extends RuntimeException> exceptionType) {
        throwIf(ObjectUtil.equal(obj1, obj2), message, exceptionType);
    }

    /**
     * 如果不相同，抛出异常
     *
     * @param obj1          要比较的对象1
     * @param obj2          要比较的对象2
     * @param message       错误信息
     * @param exceptionType 异常类型
     */
    protected static void throwIfNotEqual(Object obj1,
                                          Object obj2,
                                          String message,
                                          Class<? extends RuntimeException> exceptionType) {
        throwIf(ObjectUtil.notEqual(obj1, obj2), message, exceptionType);
    }

    /**
     * 如果相同，抛出异常（不区分大小写）
     *
     * @param str1          要比较的字符串1
     * @param str2          要比较的字符串2
     * @param message       错误信息
     * @param exceptionType 异常类型
     */
    protected static void throwIfEqualIgnoreCase(CharSequence str1,
                                                 CharSequence str2,
                                                 String message,
                                                 Class<? extends RuntimeException> exceptionType) {
        throwIf(CharSequenceUtil.equalsIgnoreCase(str1, str2), message, exceptionType);
    }

    /**
     * 如果不相同，抛出异常（不区分大小写）
     *
     * @param str1          要比较的字符串1
     * @param str2          要比较的字符串2
     * @param message       错误信息
     * @param exceptionType 异常类型
     */
    protected static void throwIfNotEqualIgnoreCase(CharSequence str1,
                                                    CharSequence str2,
                                                    String message,
                                                    Class<? extends RuntimeException> exceptionType) {
        throwIf(!CharSequenceUtil.equalsIgnoreCase(str1, str2), message, exceptionType);
    }

    /**
     * 如果条件成立，抛出异常
     *
     * @param condition     条件
     * @param message       错误信息
     * @param exceptionType 异常类型
     */
    protected static void throwIf(boolean condition, String message, Class<? extends RuntimeException> exceptionType) {
        if (condition) {
            log.error(message);
            throw ReflectUtil.newInstance(exceptionType, message);
        }
    }

    /**
     * 如果条件成立，抛出异常
     *
     * @param conditionSupplier 条件
     * @param message           错误信息
     * @param exceptionType     异常类型
     */
    protected static void throwIf(BooleanSupplier conditionSupplier,
                                  String message,
                                  Class<? extends RuntimeException> exceptionType) {
        if (null != conditionSupplier && conditionSupplier.getAsBoolean()) {
            log.error(message);
            throw ReflectUtil.newInstance(exceptionType, message);
        }
    }
}
