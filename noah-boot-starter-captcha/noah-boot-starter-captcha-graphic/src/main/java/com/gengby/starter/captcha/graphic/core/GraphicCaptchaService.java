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
