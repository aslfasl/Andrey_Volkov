package com.Volkov.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;

public class Converter {
    private static ObjectMapper objectMapper = JsonMapper.builder()
            .findAndAddModules()
            .build();

    public static <T> T convertValue(Object fromValue, Class<T> toValueType){
        return objectMapper.convertValue(fromValue, toValueType);
    }
}
