package com.gengby.starter.extension.crud.model.resp;

import cn.crane4j.annotation.Assemble;
import cn.crane4j.annotation.Mapping;
import cn.crane4j.annotation.condition.ConditionOnPropertyNotNull;
import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import com.gengby.starter.extension.crud.constant.ContainerPool;

import java.io.Serial;
import java.time.LocalDateTime;

/**
 * 详情响应基类
 *
 * @author Noah
 * @since 1.0.0
 */
public class BaseDetailResp extends BaseResp {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 修改人
     */
    @JsonIgnore
    @ConditionOnPropertyNotNull
    @Assemble(container = ContainerPool.USER_NICKNAME, props = @Mapping(ref = "updateUserString"))
    private Long updateUser;

    /**
     * 修改人
     */
    @Schema(description = "修改人", example = "李四")
    @ExcelProperty(value = "修改人", order = Integer.MAX_VALUE - 2)
    private String updateUserString;

    /**
     * 修改时间
     */
    @Schema(description = "修改时间", example = "2023-08-08 08:08:08", type = "string")
    @ExcelProperty(value = "修改时间", order = Integer.MAX_VALUE - 1)
    private LocalDateTime updateTime;

    public Long getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(Long updateUser) {
        this.updateUser = updateUser;
    }

    public String getUpdateUserString() {
        return updateUserString;
    }

    public void setUpdateUserString(String updateUserString) {
        this.updateUserString = updateUserString;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }
}
