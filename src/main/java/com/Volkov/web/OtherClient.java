package com.Volkov.web;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "test", url = "https://api.fungenerators.com")
public interface OtherClient {

    @RequestMapping(method = RequestMethod.GET,
            value = "/fact/random")
    String getFact();
}
