package com.Volkov.configuration;

import com.Volkov.dto.Converter;
import com.Volkov.service.WhatTime;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class AppConf {

    @Bean
    public ObjectMapper mapper(){
        return JsonMapper.builder()
                .findAndAddModules()
                .build();
    }

    @Bean
    public Converter creatorConverter(ObjectMapper objectMapper){
        Converter converter = new Converter();
        converter.setObjectMapper(objectMapper);
        return converter;
    }

    @Bean
    public WhatTime time() {
        return new WhatTime();
    }
}
