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

package com.gengby.starter.web.model;

import cn.hutool.core.date.DateUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.HttpStatus;

import java.io.Serial;
import java.io.Serializable;

/**
 * 响应信息
 *
 * @author Noah
 * @since 1.0.0
 */
@Schema(description = "响应信息")
public class R<T> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private static final int SUCCESS_CODE = HttpStatus.OK.value();
    private static final int FAIL_CODE = HttpStatus.INTERNAL_SERVER_ERROR.value();

    /**
     * 是否成功
     */
    @Schema(description = "是否成功", example = "true")
    private boolean success;

    /**
     * 业务状态码
     */
    @Schema(description = "业务状态码", example = "200")
    private int code;

    /**
     * 业务状态信息
     */
    @Schema(description = "业务状态信息", example = "操作成功")
    private String msg;

    /**
     * 响应数据
     */
    @Schema(description = "响应数据")
    private T data;

    /**
     * 时间戳
     */
    @Schema(description = "时间戳", example = "1691453288")
    private long timestamp = DateUtil.currentSeconds();

    private R() {
    }

    private R(boolean success, int code, String msg, T data) {
        this.success = success;
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    /**
     * 操作成功
     *
     * @param <T> 响应数据类型
     * @return R /
     */
    public static <T> R<T> ok() {
        return new R<>(true, SUCCESS_CODE, "操作成功", null);
    }

    /**
     * 操作成功
     *
     * @param data 响应数据
     * @param <T>  响应数据类型
     * @return R /
     */
    public static <T> R<T> ok(T data) {
        return new R<>(true, SUCCESS_CODE, "操作成功", data);
    }

    /**
     * 操作成功
     *
     * @param msg 业务状态信息
     * @param <T> 响应数据类型
     * @return R /
     */
    public static <T> R<T> ok(String msg) {
        return new R<>(true, SUCCESS_CODE, msg, null);
    }

    /**
     * 操作成功
     *
     * @param msg  业务状态信息
     * @param data 响应数据
     * @param <T>  响应数据类型
     * @return R /
     */
    public static <T> R<T> ok(String msg, T data) {
        return new R<>(true, SUCCESS_CODE, msg, data);
    }

    /**
     * 操作失败
     *
     * @param <T> 响应数据类型
     * @return R /
     */
    public static <T> R<T> fail() {
        return new R<>(false, FAIL_CODE, "操作失败", null);
    }

    /**
     * 操作失败
     *
     * @param msg 业务状态信息
     * @param <T> 响应数据类型
     * @return R /
     */
    public static <T> R<T> fail(String msg) {
        return new R<>(false, FAIL_CODE, msg, null);
    }

    /**
     * 操作失败
     *
     * @param data 响应数据
     * @param <T>  响应数据类型
     * @return R /
     */
    public static <T> R<T> fail(T data) {
        return new R<>(false, FAIL_CODE, "操作失败", data);
    }

    /**
     * 操作失败
     *
     * @param msg  业务状态信息
     * @param data 响应数据
     * @param <T>  响应数据类型
     * @return R /
     */
    public static <T> R<T> fail(String msg, T data) {
        return new R<>(false, FAIL_CODE, msg, data);
    }

    /**
     * 操作失败
     *
     * @param code 业务状态码
     * @param msg  业务状态信息
     * @param <T>  响应数据类型
     * @return R /
     */
    public static <T> R<T> fail(int code, String msg) {
        return new R<>(false, code, msg, null);
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
