package com.gengby.starter.log.core.model;

import com.gengby.starter.log.core.enums.Include;

import java.util.*;

/**
 * 响应信息
 *
 * @author Noah
 * @since 1.1.0
 */
public class LogResponse {

    /**
     * 状态码
     */
    private Integer status;

    /**
     * 响应头
     */
    private Map<String, String> headers;

    /**
     * 响应体（JSON 字符串）
     */
    private String body;

    /**
     * 响应参数
     */
    private Map<String, Object> param;

    public LogResponse(RecordableHttpResponse response, Set<Include> includes) {
        this.status = response.getStatus();
        this.headers = (includes.contains(Include.RESPONSE_HEADERS)) ? response.getHeaders() : null;
        if (includes.contains(Include.RESPONSE_BODY)) {
            this.body = response.getBody();
        } else if (includes.contains(Include.RESPONSE_PARAM)) {
            this.param = response.getParam();
        }
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Map<String, Object> getParam() {
        return param;
    }

    public void setParam(Map<String, Object> param) {
        this.param = param;
    }
}