package com.example.Volkov.web;

import com.example.Volkov.dto.Bird;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("home")
public class FirstEverController {



    @GetMapping
    public String someMessage(){
        return "hello";
    }

    @GetMapping("/secondMapping")
    public String anotherMessage(){
        return "bonjour";
    }

}

