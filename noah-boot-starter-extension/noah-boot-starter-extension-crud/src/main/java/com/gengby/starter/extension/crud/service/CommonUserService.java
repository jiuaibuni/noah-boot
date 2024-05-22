package com.gengby.starter.extension.crud.service;

import cn.crane4j.annotation.ContainerMethod;
import cn.crane4j.annotation.MappingType;
import com.gengby.starter.extension.crud.constant.ContainerPool;

/**
 * 公共用户业务接口
 *
 * @author Noah
 * @since 1.0.0
 */
public interface CommonUserService {

    /**
     * 根据 ID 查询昵称
     *
     * @param id ID
     * @return 昵称
     */
    @ContainerMethod(namespace = ContainerPool.USER_NICKNAME, type = MappingType.ORDER_OF_KEYS)
    String getNicknameById(Long id);
}
