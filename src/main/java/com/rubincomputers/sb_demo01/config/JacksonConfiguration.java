package com.rubincomputers.sb_demo01.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

@Configuration
public class JacksonConfiguration {

//    @Bean
//    public Jackson2ObjectMapperBuilder objectMapperBuilder() {
//        Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
//        builder.visibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
//        return builder;
//    }
}
