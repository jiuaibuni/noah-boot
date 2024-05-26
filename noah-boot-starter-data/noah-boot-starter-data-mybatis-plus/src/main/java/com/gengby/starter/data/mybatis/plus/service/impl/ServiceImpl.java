package com.gengby.starter.data.mybatis.plus.service.impl;

import cn.hutool.core.util.ClassUtil;
import com.gengby.starter.core.util.ReflectUtils;
import com.gengby.starter.core.util.validate.CheckUtil;
import com.gengby.starter.data.mybatis.plus.base.BaseMapper;
import com.gengby.starter.data.mybatis.plus.service.IService;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.List;

/**
 * 通用业务实现类
 *
 * @param <M> Mapper 接口
 * @param <T> 实体类型
 * @author Noah
 * @since 1.0.0
 */
public class ServiceImpl<M extends BaseMapper<T>, T> extends com.baomidou.mybatisplus.extension.service.impl.ServiceImpl<M, T> implements IService<T> {

    protected final List<Field> entityFields = ReflectUtils.getNonStaticFields(this.entityClass);

    @Override
    public T getById(Serializable id) {
        return this.getById(id, true);
    }

    /**
     * 根据 ID 查询
     *
     * @param id            ID
     * @param isCheckExists 是否检查存在
     * @return 实体信息
     */
    protected T getById(Serializable id, boolean isCheckExists) {
        T entity = baseMapper.selectById(id);
        if (isCheckExists) {
            CheckUtil.throwIfNotExists(entity, ClassUtil.getClassName(entityClass, true), "ID", id);
        }
        return entity;
    }
}