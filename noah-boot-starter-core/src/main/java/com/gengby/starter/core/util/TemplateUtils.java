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

package com.gengby.starter.core.util;

import cn.hutool.extra.template.Template;
import cn.hutool.extra.template.TemplateConfig;
import cn.hutool.extra.template.TemplateEngine;
import cn.hutool.extra.template.TemplateUtil;

import java.util.Map;

/**
 * 模板工具类
 *
 * @author Noah
 * @since 1.0.0
 */
public class TemplateUtils {

    private static final String DEFAULT_TEMPLATE_PARENT_PATH = "templates";

    private TemplateUtils() {
    }

    /**
     * 渲染模板
     *
     * @param templatePath 模板路径
     * @param bindingMap   绑定参数（此 Map 中的参数会替换模板中的变量）
     * @return 渲染后的内容
     */
    public static String render(String templatePath, Map<?, ?> bindingMap) {
        return render(DEFAULT_TEMPLATE_PARENT_PATH, templatePath, bindingMap);
    }

    /**
     * 渲染模板
     *
     * @param parentPath   模板父目录
     * @param templatePath 模板路径
     * @param bindingMap   绑定参数（此 Map 中的参数会替换模板中的变量）
     * @return 渲染后的内容
     */
    public static String render(String parentPath, String templatePath, Map<?, ?> bindingMap) {
        TemplateEngine engine = TemplateUtil
            .createEngine(new TemplateConfig(parentPath, TemplateConfig.ResourceMode.CLASSPATH));
        Template template = engine.getTemplate(templatePath);
        return template.render(bindingMap);
    }
}
