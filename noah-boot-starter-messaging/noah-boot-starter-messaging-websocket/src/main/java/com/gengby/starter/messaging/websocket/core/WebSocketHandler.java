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

package com.gengby.starter.messaging.websocket.core;

import cn.hutool.core.convert.Convert;
import com.gengby.starter.messaging.websocket.dao.WebSocketSessionDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import com.gengby.starter.messaging.websocket.autoconfigure.WebSocketProperties;

import java.io.IOException;

/**
 * WebSocket 处理器
 *
 * @author Noah
 * @since 1.0.0
 */
public class WebSocketHandler extends TextWebSocketHandler {

    private static final Logger log = LoggerFactory.getLogger(WebSocketHandler.class);
    private final WebSocketProperties webSocketProperties;
    private final WebSocketSessionDao webSocketSessionDao;

    public WebSocketHandler(WebSocketProperties webSocketProperties, WebSocketSessionDao webSocketSessionDao) {
        this.webSocketProperties = webSocketProperties;
        this.webSocketSessionDao = webSocketSessionDao;
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String clientId = this.getClientId(session);
        log.info("WebSocket receive message. clientId: {}, message: {}.", clientId, message.getPayload());
        super.handleTextMessage(session, message);
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        String clientId = this.getClientId(session);
        webSocketSessionDao.add(clientId, session);
        log.info("WebSocket client connect successfully. clientId: {}.", clientId);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        String clientId = this.getClientId(session);
        webSocketSessionDao.delete(clientId);
        log.info("WebSocket client connect closed. clientId: {}.", clientId);
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws IOException {
        String clientId = this.getClientId(session);
        if (session.isOpen()) {
            session.close();
        }
        webSocketSessionDao.delete(clientId);
    }

    /**
     * 获取客户端 ID
     * 
     * @param session 会话
     * @return 客户端 ID
     */
    private String getClientId(WebSocketSession session) {
        return Convert.toStr(session.getAttributes().get(webSocketProperties.getClientIdKey()));
    }
}
