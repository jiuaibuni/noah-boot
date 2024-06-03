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

package com.gengby.starter.data.core.enums;

/**
 * 查询类型枚举
 *
 * @author Noah
 * @since 1.0.0
 */
public enum QueryType {

    /**
     * 等于 =，例如：WHERE age = 18
     */
    EQ,

    /**
     * 不等于 !=，例如：WHERE age != 18
     */
    NE,

    /**
     * 大于 >，例如：WHERE age > 18
     */
    GT,

    /**
     * 大于等于 >= ，例如：WHERE age >= 18
     */
    GE,

    /**
     * 小于 <，例如：WHERE age < 18
     */
    LT,

    /**
     * 小于等于 <=，例如：WHERE age <= 18
     */
    LE,

    /**
     * 范围查询，例如：WHERE age BETWEEN 10 AND 18
     */
    BETWEEN,

    /**
     * LIKE '%值%'，例如：WHERE nickname LIKE '%s%'
     */
    LIKE,

    /**
     * LIKE '%值'，例如：WHERE nickname LIKE '%s'
     */
    LIKE_LEFT,

    /**
     * LIKE '值%'，例如：WHERE nickname LIKE 's%'
     */
    LIKE_RIGHT,

    /**
     * 包含查询，例如：WHERE age IN (10, 20, 30)
     */
    IN,

    /**
     * 不包含查询，例如：WHERE age NOT IN (20, 30)
     */
    NOT_IN,

    /**
     * 空查询，例如：WHERE email IS NULL
     */
    IS_NULL,

    /**
     * 非空查询，例如：WHERE email IS NOT NULL
     */
    IS_NOT_NULL,;
}
