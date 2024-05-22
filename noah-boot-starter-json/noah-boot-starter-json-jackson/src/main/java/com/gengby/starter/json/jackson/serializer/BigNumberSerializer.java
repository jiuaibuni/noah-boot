package com.gengby.starter.json.jackson.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;
import com.fasterxml.jackson.databind.ser.std.NumberSerializer;

import java.io.IOException;

/**
 * 大数值序列化器
 * <p>
 * 将 JS 取值范围之外的数值转换为字符串
 * </p>
 *
 * @author Noah
 * @since 1.0.0
 */
@JacksonStdImpl
public class BigNumberSerializer extends NumberSerializer {

    /**
     * 静态实例
     */
    public static final BigNumberSerializer SERIALIZER_INSTANCE = new BigNumberSerializer(Number.class);
    /**
     * JS：Number.MAX_SAFE_INTEGER
     */
    private static final long MAX_SAFE_INTEGER = 9007199254740991L;
    /**
     * JS：Number.MIN_SAFE_INTEGER
     */
    private static final long MIN_SAFE_INTEGER = -9007199254740991L;

    public BigNumberSerializer(Class<? extends Number> rawType) {
        super(rawType);
    }

    @Override
    public void serialize(Number value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        if (value.longValue() > MIN_SAFE_INTEGER && value.longValue() < MAX_SAFE_INTEGER) {
            super.serialize(value, gen, provider);
        } else {
            gen.writeString(value.toString());
        }
    }
}