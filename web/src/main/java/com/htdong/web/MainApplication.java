package com.htdong.web;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication(scanBasePackages = {"com.htdong"})
@EnableScheduling
@EnableDubbo(scanBasePackages = {"com.htdong"}, multipleConfig = true)
@Slf4j
public class MainApplication {

    public static void main(String[] args) {
        System.setProperty("spring.cloud.compatibility-verifier.enabled", "false");
        try {
            SpringApplication.run(MainApplication.class, args);
        } catch (Throwable e) {
            log.error("main.", e);
        }
    }
}