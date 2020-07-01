package com.wangjun.config.web;

import java.io.IOException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

/**
 * Json序列化配置
 *
 * @author wangjun
 * @date 2020-03-20 9:42
 */
@Configuration
public class JacksonConfig {

    @Bean
    public ObjectMapper jacksonObjectMapper(Jackson2ObjectMapperBuilder builder) {
        ObjectMapper objectMapper = builder.createXmlMapper(false).build();
        /*
          可配置项： 1.Include.Include.ALWAYS :默认
          2.Include.NON_DEFAULT : 默认值不序列化
          3.Include.NON_EMPTY : 属性为 空（""） 或者为 NULL都不序列化
          4.Include.NON_NULL : 属性为NULL 不序列化
           objectMapper. setSerializationInclusion(JsonInclude.Include.NON_NULL);
          序列化时,将所有的long变成string
         */
        SimpleModule module = new SimpleModule();
        module.addSerializer(Long.class, ToStringSerializer.instance);
        module.addSerializer(Long.TYPE, ToStringSerializer.instance);
        /*
       将所有的String 返回 null 时 返回 "" ;
         */
        objectMapper.getSerializerProvider().setNullValueSerializer(new JsonSerializer<Object>() {
            @Override
            public void serialize(Object o, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
                jsonGenerator.writeString("");
            }
        });
        objectMapper.registerModule(module);
        return objectMapper;
    }
}