//package com.Volkov.service;
//
//import com.Volkov.web.OtherClient;
//import lombok.RequiredArgsConstructor;
//import lombok.SneakyThrows;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Service;
//
//import javax.annotation.PostConstruct;
//
//@Service
//@RequiredArgsConstructor
//public class WeatherGetter {
//
//    private final OtherClient otherClient;
//
//    @Scheduled(initialDelay = 7000L, fixedRate = 14000L)
//    public void clicker() {
//        String weather = otherClient.getWeather();
//        String forecast = weather.substring(weather.indexOf("forecast")-1, weather.indexOf("debug")) ;
//        System.out.println(forecast);
//    }
//
//    @SneakyThrows
//    @PostConstruct
//    public void init() {
//        clicker();
//    }
//}
