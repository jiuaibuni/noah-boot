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