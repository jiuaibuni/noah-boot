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

package com.gengby.starter.extension.crud.annotation;

import java.lang.annotation.*;

/**
 * 树结构字段
 *
 * @author Noah
 * @see cn.hutool.core.lang.tree.TreeNodeConfig
 * @since 1.0.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TreeField {

    /**
     * ID 字段名
     *
     * @return ID 字段名
     */
    String value() default "key";

    /**
     * 父 ID 字段名
     *
     * @return 父 ID 字段名
     */
    String parentIdKey() default "parentId";

    /**
     * 名称字段名
     *
     * @return 名称字段名
     */
    String nameKey() default "title";

    /**
     * 排序字段名
     *
     * @return 排序字段名
     */
    String weightKey() default "sort";

    /**
     * 子列表字段名
     *
     * @return 子列表字段名
     */
    String childrenKey() default "children";

    /**
     * 递归深度（< 0 不限制）
     *
     * @return 递归深度
     */
    int deep() default -1;
}
