package com.gengby.starter.log.core.dao;

import com.gengby.starter.log.core.model.LogRecord;

import java.util.Collections;
import java.util.List;

/**
 * 日志持久层接口
 *
 * @author Noah
 * @since 1.1.0
 */
public interface LogDao {

    /**
     * 查询日志列表
     *
     * @return 日志列表
     */
    default List<LogRecord> list() {
        return Collections.emptyList();
    }

    /**
     * 记录日志
     *
     * @param logRecord 日志信息
     */
    void add(LogRecord logRecord);
}
