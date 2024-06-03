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

package com.gengby.starter.captcha.graphic.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;
import com.gengby.starter.captcha.graphic.enums.GraphicCaptchaType;
import com.gengby.starter.core.constant.PropertiesConstants;

/**
 * 图形验证码配置属性
 *
 * @author Noah
 * @since 1.0.0
 */
@ConfigurationProperties(PropertiesConstants.CAPTCHA_GRAPHIC)
public class GraphicCaptchaProperties {

    /**
     * 是否启用图形验证码
     */
    private boolean enabled = true;

    /**
     * 类型
     */
    private GraphicCaptchaType type = GraphicCaptchaType.SPEC;

    /**
     * 内容长度
     */
    private int length = 4;

    /**
     * 宽度
     */
    private int width = 111;

    /**
     * 高度
     */
    private int height = 36;

    /**
     * 字体
     */
    private String fontName;

    /**
     * 字体大小
     */
    private int fontSize = 25;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public GraphicCaptchaType getType() {
        return type;
    }

    public void setType(GraphicCaptchaType type) {
        this.type = type;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getFontName() {
        return fontName;
    }

    public void setFontName(String fontName) {
        this.fontName = fontName;
    }

    public int getFontSize() {
        return fontSize;
    }

    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }
}
