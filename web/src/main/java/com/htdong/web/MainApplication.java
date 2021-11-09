package com.htdong.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication(scanBasePackages = { "com.htdong" })
@EnableScheduling
@Slf4j
public class MainApplication {

    public static void main(String[] args) {
        try {
            SpringApplication.run(MainApplication.class, args);
        } catch (Throwable e) {
            log.error("main.", e);
        }
    }
}