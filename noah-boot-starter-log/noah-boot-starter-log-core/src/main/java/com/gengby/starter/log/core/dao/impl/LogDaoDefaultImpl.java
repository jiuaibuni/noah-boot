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