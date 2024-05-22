package com.gengby.starter.json.jackson.autoconfigure;

import cn.hutool.core.date.DatePattern;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import com.gengby.starter.core.util.GeneralPropertySourceFactory;
import com.gengby.starter.json.jackson.serializer.BigNumberSerializer;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;

/**
 * Jackson 自动配置
 *
 * @author Noah
 * @since 1.0.0
 */
@AutoConfiguration
@PropertySource(value = "classpath:default-json-jackson.yml", factory = GeneralPropertySourceFactory.class)
public class JacksonAutoConfiguration {
    private static final Logger log = LoggerFactory.getLogger(JacksonAutoConfiguration.class);

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
        return builder -> {
            // 针对大数值的序列化处理
            JavaTimeModule javaTimeModule = new JavaTimeModule();
            javaTimeModule.addSerializer(Long.class, BigNumberSerializer.SERIALIZER_INSTANCE);
            javaTimeModule.addSerializer(Long.TYPE, BigNumberSerializer.SERIALIZER_INSTANCE);
            javaTimeModule.addSerializer(BigInteger.class, BigNumberSerializer.SERIALIZER_INSTANCE);
            // 针对时间类型：LocalDateTime 的序列化和反序列化处理
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DatePattern.NORM_DATETIME_PATTERN);
            javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(dateTimeFormatter));
            javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(dateTimeFormatter));
            // 针对时间类型：LocalDate 的序列化和反序列化处理
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(DatePattern.NORM_DATE_PATTERN);
            javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer(dateFormatter));
            javaTimeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer(dateFormatter));
            // 针对时间类型：LocalTime 的序列化和反序列化处理
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern(DatePattern.NORM_TIME_PATTERN);
            javaTimeModule.addSerializer(LocalTime.class, new LocalTimeSerializer(timeFormatter));
            javaTimeModule.addDeserializer(LocalTime.class, new LocalTimeDeserializer(timeFormatter));
            builder.timeZone(TimeZone.getDefault());
            builder.modules(javaTimeModule);
            log.debug("[Noah Boot] - Auto Configuration 'Jackson' completed initialization.");
        };
    }
}
