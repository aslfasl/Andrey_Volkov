package com.Volkov.web;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "test", url = "https://api.waqi.info/")
public interface OtherClient {

    @RequestMapping(method = RequestMethod.GET,
            value = "/feed/beijing/?token=82242192e0d6a63b59212cbe97fcaa82610f4d60")
    String getWeather();
}
