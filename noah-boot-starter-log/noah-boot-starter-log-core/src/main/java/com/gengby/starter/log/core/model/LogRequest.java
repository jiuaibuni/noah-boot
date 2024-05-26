package com.gengby.starter.log.core.model;

import cn.hutool.core.text.CharSequenceUtil;
import com.gengby.starter.web.util.ServletUtil;
import org.springframework.http.HttpHeaders;
import com.gengby.starter.core.util.IpUtils;
import com.gengby.starter.log.core.enums.Include;

import java.net.URI;
import java.util.Map;
import java.util.Set;

/**
 * 请求信息
 *
 * @author Noah
 * @since 1.1.0
 */
public class LogRequest {

    /**
     * 请求方式
     */
    private String method;

    /**
     * 请求 URL
     */
    private URI url;

    /**
     * IP
     */
    private String ip;

    /**
     * 请求头
     */
    private Map<String, String> headers;

    /**
     * 请求体（JSON 字符串）
     */
    private String body;

    /**
     * 请求参数
     */
    private Map<String, Object> param;

    /**
     * IP 归属地
     */
    private String address;

    /**
     * 浏览器
     */
    private String browser;

    /**
     * 操作系统
     */
    private String os;

    public LogRequest(RecordableHttpRequest request, Set<Include> includes) {
        this.method = request.getMethod();
        this.url = request.getUrl();
        this.ip = request.getIp();
        this.headers = (includes.contains(Include.REQUEST_HEADERS)) ? request.getHeaders() : null;
        if (includes.contains(Include.REQUEST_BODY)) {
            this.body = request.getBody();
        } else if (includes.contains(Include.REQUEST_PARAM)) {
            this.param = request.getParam();
        }
        this.address = (includes.contains(Include.IP_ADDRESS)) ? IpUtil.getAddress(this.ip) : null;
        if (null == this.headers) {
            return;
        }
        String userAgentString = this.headers.entrySet()
            .stream()
            .filter(h -> HttpHeaders.USER_AGENT.equalsIgnoreCase(h.getKey()))
            .map(Map.Entry::getValue)
            .findFirst()
            .orElse(null);
        if (CharSequenceUtil.isNotBlank(userAgentString)) {
            this.browser = (includes.contains(Include.BROWSER)) ? ServletUtil.getBrowser(userAgentString) : null;
            this.os = (includes.contains(Include.OS)) ? ServletUtil.getOs(userAgentString) : null;
        }
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public URI getUrl() {
        return url;
    }

    public void setUrl(URI url) {
        this.url = url;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBrowser() {
        return browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }
}