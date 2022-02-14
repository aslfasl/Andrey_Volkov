package com.Volkov.configuration;

import com.Volkov.dto.Converter;
import com.Volkov.service.ValidationService;
import com.Volkov.service.WhatTime;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Collections;


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

    @Bean
    public ValidationService validationServiceBean() {return new ValidationService();}

    @Bean
    public Docket swaggerConfig() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select() // API selector builder
                .paths(PathSelectors.ant("/**"))
                .apis(RequestHandlerSelectors.basePackage("com.Volkov"))
                .build()
                .apiInfo(apiDetails());
    }

    private ApiInfo apiDetails() {
        return new ApiInfo(
                "Garage API",
                "Swagger task",
                "1.0",
                "Free to use",
                new springfox.documentation.service.Contact("Andrey Volkov", "url@url.url", "soupec@yandex.ru"),
                "API License",
                "http://license.qq",
                Collections.emptyList());
    }
}
