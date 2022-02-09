package com.Volkov;

import com.Volkov.dto.Converter;
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
    public Converter converter(ObjectMapper mapper){
        return new Converter(mapper);
    }
}
