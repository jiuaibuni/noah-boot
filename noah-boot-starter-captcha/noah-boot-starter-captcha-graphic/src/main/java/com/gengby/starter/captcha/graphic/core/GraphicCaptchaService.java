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

package com.gengby.starter.captcha.graphic.core;

import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.text.CharSequenceUtil;
import com.wf.captcha.base.Captcha;
import com.gengby.starter.captcha.graphic.autoconfigure.GraphicCaptchaProperties;

import java.awt.*;

/**
 * 图形验证码服务接口
 *
 * @author Noah
 * @since 1.4.0
 */
public class GraphicCaptchaService {

    private final GraphicCaptchaProperties properties;

    public GraphicCaptchaService(GraphicCaptchaProperties properties) {
        this.properties = properties;
    }

    /**
     * 获取验证码实例
     *
     * @return 验证码实例
     */
    public Captcha getCaptcha() {
        Captcha captcha = ReflectUtil.newInstance(properties.getType().getCaptchaImpl(), properties
            .getWidth(), properties.getHeight());
        captcha.setLen(properties.getLength());
        if (CharSequenceUtil.isNotBlank(properties.getFontName())) {
            captcha.setFont(new Font(properties.getFontName(), Font.PLAIN, properties.getFontSize()));
        }
        return captcha;
    }
}
