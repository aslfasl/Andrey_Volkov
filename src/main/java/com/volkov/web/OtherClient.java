package com.volkov.web;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "test", url = "https://api.waqi.info/")
public interface OtherClient {

    @GetMapping(value = "/feed/beijing/?token=82242192e0d6a63b59212cbe97fcaa82610f4d60")
    String getWeather();
}
