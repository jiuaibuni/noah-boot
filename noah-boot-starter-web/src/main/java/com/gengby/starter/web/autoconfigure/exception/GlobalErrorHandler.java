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

package com.gengby.starter.web.autoconfigure.exception;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gengby.starter.web.model.R;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorViewResolver;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 全局错误处理器
 *
 * @author Noah
 * @since 1.0.0
 */
@RestController
public class GlobalErrorHandler extends BasicErrorController {

    private static final Logger log = LoggerFactory.getLogger(GlobalErrorHandler.class);

    @Resource
    private ObjectMapper objectMapper;

    public GlobalErrorHandler(ErrorAttributes errorAttributes,
                              ServerProperties serverProperties,
                              List<ErrorViewResolver> errorViewResolvers) {
        super(errorAttributes, serverProperties.getError(), errorViewResolvers);
    }

    @Override
    public ModelAndView errorHtml(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> errorAttributeMap = super.getErrorAttributes(request, super.getErrorAttributeOptions(request, MediaType.TEXT_HTML));
        String path = (String)errorAttributeMap.get("path");
        HttpStatus status = super.getStatus(request);
        R<Object> result = R.fail(status.value(), (String)errorAttributeMap.get("error"));
        result.setData(path);
        try {
            response.setStatus(HttpStatus.OK.value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            objectMapper.writeValue(response.getWriter(), result);
        } catch (IOException e) {
            log.error("请求地址 [{}]，默认错误处理时发生 IO 异常。", path, e);
        }
        if (log.isErrorEnabled()) {
            log.error("请求地址 [{}]，发生错误，错误信息：{}。", path, JSONUtil.toJsonStr(errorAttributeMap));
        }
        return null;
    }

    @Override
    public ResponseEntity<Map<String, Object>> error(HttpServletRequest request) {
        Map<String, Object> errorAttributeMap = super.getErrorAttributes(request, super.getErrorAttributeOptions(request, MediaType.ALL));
        String path = (String)errorAttributeMap.get("path");
        HttpStatus status = super.getStatus(request);
        R<Object> result = R.fail(status.value(), (String)errorAttributeMap.get("error"));
        result.setData(path);
        if (log.isErrorEnabled()) {
            log.error("请求地址 [{}]，发生错误，错误信息：{}。", path, JSONUtil.toJsonStr(errorAttributeMap));
        }
        return new ResponseEntity<>(BeanUtil.beanToMap(result), HttpStatus.OK);
    }
}
