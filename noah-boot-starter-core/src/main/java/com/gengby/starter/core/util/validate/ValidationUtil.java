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
import com.gengby.starter.core.exception.BadRequestException;

import java.util.function.BooleanSupplier;

/**
 * 基本参数校验工具类（抛出 400 BadRequestException）
 *
 * @author Noah
 * @see BadRequestException
 * @since 1.0.0
 */
public class ValidationUtil extends Validator {

    private static final Class<BadRequestException> EXCEPTION_TYPE = BadRequestException.class;

    private ValidationUtil() {
    }

    /**
     * 如果为空，抛出异常
     *
     * @param obj      被检测的对象
     * @param template 异常信息模板，被替换的部分用 {} 表示，如果模板为 null，返回 "null"
     * @param params   参数值
     */
    public static void throwIfNull(Object obj, String template, Object... params) {
        throwIfNull(obj, CharSequenceUtil.format(template, params), EXCEPTION_TYPE);
    }

    /**
     * 如果不为空，抛出异常
     *
     * @param obj      被检测的对象
     * @param template 异常信息模板，被替换的部分用 {} 表示，如果模板为 null，返回 "null"
     * @param params   参数值
     */
    public static void throwIfNotNull(Object obj, String template, Object... params) {
        throwIfNotNull(obj, CharSequenceUtil.format(template, params), EXCEPTION_TYPE);
    }

    /**
     * 如果为空，抛出异常
     *
     * @param obj      被检测的对象
     * @param template 异常信息模板，被替换的部分用 {} 表示，如果模板为 null，返回 "null"
     * @param params   参数值
     */
    public static void throwIfEmpty(Object obj, String template, Object... params) {
        throwIfEmpty(obj, CharSequenceUtil.format(template, params), EXCEPTION_TYPE);
    }

    /**
     * 如果不为空，抛出异常
     *
     * @param obj      被检测的对象
     * @param template 异常信息模板，被替换的部分用 {} 表示，如果模板为 null，返回 "null"
     * @param params   参数值
     */
    public static void throwIfNotEmpty(Object obj, String template, Object... params) {
        throwIfNotEmpty(obj, CharSequenceUtil.format(template, params), EXCEPTION_TYPE);
    }

    /**
     * 如果为空，抛出异常
     *
     * @param str      被检测的字符串
     * @param template 异常信息模板，被替换的部分用 {} 表示，如果模板为 null，返回 "null"
     * @param params   参数值
     */
    public static void throwIfBlank(CharSequence str, String template, Object... params) {
        throwIfBlank(str, CharSequenceUtil.format(template, params), EXCEPTION_TYPE);
    }

    /**
     * 如果不为空，抛出异常
     *
     * @param str      被检测的字符串
     * @param template 异常信息模板，被替换的部分用 {} 表示，如果模板为 null，返回 "null"
     * @param params   参数值
     */
    public static void throwIfNotBlank(CharSequence str, String template, Object... params) {
        throwIfNotBlank(str, CharSequenceUtil.format(template, params), EXCEPTION_TYPE);
    }

    /**
     * 如果相同，抛出异常
     *
     * @param obj1     要比较的对象1
     * @param obj2     要比较的对象2
     * @param template 异常信息模板，被替换的部分用 {} 表示，如果模板为 null，返回 "null"
     * @param params   参数值
     */
    public static void throwIfEqual(Object obj1, Object obj2, String template, Object... params) {
        throwIfEqual(obj1, obj2, CharSequenceUtil.format(template, params), EXCEPTION_TYPE);
    }

    /**
     * 如果不相同，抛出异常
     *
     * @param obj1     要比较的对象1
     * @param obj2     要比较的对象2
     * @param template 异常信息模板，被替换的部分用 {} 表示，如果模板为 null，返回 "null"
     * @param params   参数值
     */
    public static void throwIfNotEqual(Object obj1, Object obj2, String template, Object... params) {
        throwIfNotEqual(obj1, obj2, CharSequenceUtil.format(template, params), EXCEPTION_TYPE);
    }

    /**
     * 如果相同，抛出异常（不区分大小写）
     *
     * @param str1     要比较的字符串1
     * @param str2     要比较的字符串2
     * @param template 异常信息模板，被替换的部分用 {} 表示，如果模板为 null，返回 "null"
     * @param params   参数值
     */
    public static void throwIfEqualIgnoreCase(CharSequence str1, CharSequence str2, String template, Object... params) {
        throwIfEqualIgnoreCase(str1, str2, CharSequenceUtil.format(template, params), EXCEPTION_TYPE);
    }

    /**
     * 如果不相同，抛出异常（不区分大小写）
     *
     * @param str1     要比较的字符串1
     * @param str2     要比较的字符串2
     * @param template 异常信息模板，被替换的部分用 {} 表示，如果模板为 null，返回 "null"
     * @param params   参数值
     */
    public static void throwIfNotEqualIgnoreCase(CharSequence str1,
                                                 CharSequence str2,
                                                 String template,
                                                 Object... params) {
        throwIfNotEqualIgnoreCase(str1, str2, CharSequenceUtil.format(template, params), EXCEPTION_TYPE);
    }

    /**
     * 如果条件成立，抛出异常
     *
     * @param condition 条件
     * @param template  异常信息模板，被替换的部分用 {} 表示，如果模板为 null，返回 "null"
     * @param params    参数值
     */
    public static void throwIf(boolean condition, String template, Object... params) {
        throwIf(condition, CharSequenceUtil.format(template, params), EXCEPTION_TYPE);
    }

    /**
     * 如果条件成立，抛出异常
     *
     * @param conditionSupplier 条件
     * @param template          异常信息模板，被替换的部分用 {} 表示，如果模板为 null，返回 "null"
     * @param params            参数值
     */
    public static void throwIf(BooleanSupplier conditionSupplier, String template, Object... params) {
        throwIf(conditionSupplier, CharSequenceUtil.format(template, params), EXCEPTION_TYPE);
    }
}
