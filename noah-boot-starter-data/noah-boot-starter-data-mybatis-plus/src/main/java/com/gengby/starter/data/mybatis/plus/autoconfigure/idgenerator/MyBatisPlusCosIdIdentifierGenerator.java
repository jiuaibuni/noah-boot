package com.gengby.starter.data.mybatis.plus.autoconfigure.idgenerator;

import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import me.ahoo.cosid.snowflake.SnowflakeId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;

/**
 * MyBatis Plus ID 生成器 - CosId
 *
 * @author Noah
 * @since 1.4.0
 */
public class MyBatisPlusCosIdIdentifierGenerator implements IdentifierGenerator {

    @Qualifier("__share__SnowflakeId")
    @Lazy
    @Autowired
    private SnowflakeId snowflakeId;

    @Override
    public Number nextId(Object entity) {
        return snowflakeId.generate();
    }
}
