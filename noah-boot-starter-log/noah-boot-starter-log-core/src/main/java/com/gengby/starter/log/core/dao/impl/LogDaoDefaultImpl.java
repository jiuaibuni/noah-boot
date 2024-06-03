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

package com.gengby.starter.log.core.dao.impl;

import com.gengby.starter.log.core.dao.LogDao;
import com.gengby.starter.log.core.model.LogRecord;

import java.util.LinkedList;
import java.util.List;

/**
 * 日志持久层接口默认实现类（基于内存）
 *
 * @author Dave Syer（Spring Boot Actuator）
 * @author Olivier Bourgain（Spring Boot Actuator）
 * @author Noah
 * @since 1.1.0
 */
public class LogDaoDefaultImpl implements LogDao {

    /**
     * 容量
     */
    private int capacity = 100;

    /**
     * 是否降序
     */
    private boolean reverse = true;

    /**
     * 日志列表
     */
    private final List<LogRecord> logRecords = new LinkedList<>();

    @Override
    public List<LogRecord> list() {
        synchronized (this.logRecords) {
            return List.copyOf(this.logRecords);
        }
    }

    @Override
    public void add(LogRecord logRecord) {
        synchronized (this.logRecords) {
            while (this.logRecords.size() >= this.capacity) {
                this.logRecords.remove(this.reverse ? this.capacity - 1 : 0);
            }
            if (this.reverse) {
                this.logRecords.add(0, logRecord);
            } else {
                this.logRecords.add(logRecord);
            }
        }
    }

    /**
     * 设置内存中存储的最大日志容量
     *
     * @param capacity 容量
     */
    public void setCapacity(int capacity) {
        synchronized (this.logRecords) {
            this.capacity = capacity;
        }
    }

    /**
     * 设置是否降序
     *
     * @param reverse 是否降序（默认：true）
     */
    public void setReverse(boolean reverse) {
        synchronized (this.logRecords) {
            this.reverse = reverse;
        }
    }
}