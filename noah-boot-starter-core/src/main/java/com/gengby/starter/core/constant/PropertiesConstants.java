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

package com.gengby.starter.core.constant;

/**
 * 配置属性相关常量
 *
 * @author Noah
 * @since 1.0.0
 */
public class PropertiesConstants {

    /**
     * Noah Boot
     */
    public static final String NOAH_BOOT = "noah-boot";

    /**
     * 启用配置
     */
    public static final String ENABLED = "enabled";

    /**
     * 线程池配置
     */
    public static final String THREAD_POOL = NOAH_BOOT + StringConstants.DOT + "thread-pool";

    /**
     * Spring Doc 配置
     */
    public static final String SPRINGDOC = "springdoc";

    /**
     * Spring Doc Swagger UI 配置
     */
    public static final String SPRINGDOC_SWAGGER_UI = SPRINGDOC + StringConstants.DOT + "swagger-ui";

    /**
     * 安全配置
     */
    public static final String SECURITY = NOAH_BOOT + StringConstants.DOT + "security";

    /**
     * 密码编解码配置
     */
    public static final String PASSWORD = SECURITY + StringConstants.DOT + "password";

    /**
     * 加/解密配置
     */
    public static final String CRYPTO = SECURITY + StringConstants.DOT + "crypto";

    /**
     * Web 配置
     */
    public static final String WEB = NOAH_BOOT + StringConstants.DOT + "web";

    /**
     * 跨域配置
     */
    public static final String CORS = WEB + StringConstants.DOT + "cors";

    /**
     * 链路配置
     */
    public static final String TRACE = WEB + StringConstants.DOT + "trace";

    /**
     * XSS 配置
     */
    public static final String XSS = WEB + StringConstants.DOT + "xss";

    /**
     * 日志配置
     */
    public static final String LOG = NOAH_BOOT + StringConstants.DOT + "log";

    /**
     * 存储配置
     */
    public static final String STORAGE = NOAH_BOOT + StringConstants.DOT + "storage";

    /**
     * 本地存储配置
     */
    public static final String STORAGE_LOCAL = STORAGE + StringConstants.DOT + "local";

    /**
     * 验证码配置
     */
    public static final String CAPTCHA = NOAH_BOOT + StringConstants.DOT + "captcha";

    /**
     * 图形验证码配置
     */
    public static final String CAPTCHA_GRAPHIC = CAPTCHA + StringConstants.DOT + "graphic";

    /**
     * 行为验证码配置
     */
    public static final String CAPTCHA_BEHAVIOR = CAPTCHA + StringConstants.DOT + "behavior";

    private PropertiesConstants() {
    }
}
