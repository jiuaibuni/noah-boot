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

package com.gengby.starter.log.core.model;

import com.gengby.starter.log.core.enums.Include;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.util.Set;

/**
 * 日志信息
 *
 * @author Dave Syer（Spring Boot Actuator）
 * @author Andy Wilkinson（Spring Boot Actuator）
 * @author Phillip Webb（Spring Boot Actuator）
 * @author Noah
 * @since 1.1.0
 */
public class LogRecord {

    /**
     * 描述
     */
    private String description;

    /**
     * 模块
     */
    private String module;

    /**
     * 请求信息
     */
    private LogRequest request;

    /**
     * 响应信息
     */
    private LogResponse response;

    /**
     * 耗时
     */
    private Duration timeTaken;

    /**
     * 时间戳
     */
    private final Instant timestamp;

    public LogRecord(Instant timestamp, LogRequest request, LogResponse response, Duration timeTaken) {
        this.timestamp = timestamp;
        this.request = request;
        this.response = response;
        this.timeTaken = timeTaken;
    }

    /**
     * 开始记录日志
     *
     * @param request 请求信息
     * @return 日志记录器
     */
    public static Started start(RecordableHttpRequest request) {
        return start(Clock.systemUTC(), request);
    }

    /**
     * 开始记录日志
     *
     * @param timestamp 开始时间
     * @param request   请求信息
     * @return 日志记录器
     */
    public static Started start(Clock timestamp, RecordableHttpRequest request) {
        return new Started(timestamp, request);
    }

    /**
     * 日志记录器
     */
    public static final class Started {

        private final Instant timestamp;

        private final RecordableHttpRequest request;

        private Started(Clock clock, RecordableHttpRequest request) {
            this.timestamp = Instant.now(clock);
            this.request = request;
        }

        /**
         * 结束日志记录
         *
         * @param response 响应信息
         * @param includes 包含信息
         * @return 日志记录
         */
        public LogRecord finish(RecordableHttpResponse response, Set<Include> includes) {
            return finish(Clock.systemUTC(), response, includes);
        }

        /**
         * 结束日志记录
         *
         * @param clock    时间
         * @param response 响应信息
         * @param includes 包含信息
         * @return 日志记录
         */
        public LogRecord finish(Clock clock, RecordableHttpResponse response, Set<Include> includes) {
            LogRequest logRequest = new LogRequest(this.request, includes);
            LogResponse logResponse = new LogResponse(response, includes);
            Duration duration = Duration.between(this.timestamp, Instant.now(clock));
            return new LogRecord(this.timestamp, logRequest, logResponse, duration);
        }
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public LogRequest getRequest() {
        return request;
    }

    public void setRequest(LogRequest request) {
        this.request = request;
    }

    public LogResponse getResponse() {
        return response;
    }

    public void setResponse(LogResponse response) {
        this.response = response;
    }

    public Duration getTimeTaken() {
        return timeTaken;
    }

    public void setTimeTaken(Duration timeTaken) {
        this.timeTaken = timeTaken;
    }

    public Instant getTimestamp() {
        return timestamp;
    }
}
