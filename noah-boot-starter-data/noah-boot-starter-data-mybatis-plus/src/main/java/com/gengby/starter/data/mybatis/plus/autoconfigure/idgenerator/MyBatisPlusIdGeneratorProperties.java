package com.gengby.starter.data.mybatis.plus.autoconfigure.idgenerator;

import com.gengby.starter.data.mybatis.plus.enums.MyBatisPlusIdGeneratorType;

/**
 * MyBatis ID 生成器配置属性
 *
 * @author Noah
 * @since 1.4.0
 */
public class MyBatisPlusIdGeneratorProperties {

    /**
     * ID 生成器类型
     */
    private MyBatisPlusIdGeneratorType type = MyBatisPlusIdGeneratorType.DEFAULT;

    public MyBatisPlusIdGeneratorType getType() {
        return type;
    }

    public void setType(MyBatisPlusIdGeneratorType type) {
        this.type = type;
    }
}
