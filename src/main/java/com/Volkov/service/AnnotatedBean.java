package com.Volkov.service;

import com.Volkov.web.OtherClient;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
@RequiredArgsConstructor
public class AnnotatedBean {

    private final OtherClient otherClient;

    @Scheduled(initialDelay = 1000L, fixedRate = 5000L)
    public void clicker() {
        System.out.println("Random fact: " + otherClient.getFact());
    }

    @SneakyThrows
    @PostConstruct
    public void init() {
        clicker();
    }
}
