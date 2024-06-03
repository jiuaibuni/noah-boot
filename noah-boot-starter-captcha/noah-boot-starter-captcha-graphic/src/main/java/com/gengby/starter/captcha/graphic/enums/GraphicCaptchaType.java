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

package com.gengby.starter.captcha.graphic.enums;

import com.wf.captcha.*;
import com.wf.captcha.base.Captcha;

/**
 * 图形验证码类型枚举
 *
 * @author Noah
 * @since 1.0.0
 */
public enum GraphicCaptchaType {

    /**
     * 算术
     */
    ARITHMETIC(ArithmeticCaptcha.class),

    /**
     * 中文
     */
    CHINESE(ChineseCaptcha.class),

    /**
     * 中文闪图
     */
    CHINESE_GIF(ChineseGifCaptcha.class),

    /**
     * 闪图
     */
    GIF(GifCaptcha.class),

    /**
     * 特殊类型
     */
    SPEC(SpecCaptcha.class),;

    /**
     * 验证码实现
     */
    private final Class<? extends Captcha> captchaImpl;

    GraphicCaptchaType(Class<? extends Captcha> captchaImpl) {
        this.captchaImpl = captchaImpl;
    }

    public Class<? extends Captcha> getCaptchaImpl() {
        return captchaImpl;
    }
}