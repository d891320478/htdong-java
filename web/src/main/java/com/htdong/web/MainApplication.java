package com.htdong.web;

import java.util.Map;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.htdong.common.util.LocalConfigUtil;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication(scanBasePackages = {"com.htdong"})
@EnableScheduling
@EnableDubbo(scanBasePackages = {"com.htdong"}, multipleConfig = true)
@Slf4j
public class MainApplication {

    public static void main(String[] args) {
        Map<String, String> map = LocalConfigUtil.getMap(System.getProperty("local.conf.file"));
        for (Map.Entry<String, String> iter : map.entrySet()) {
            System.setProperty(iter.getKey(), iter.getValue());
        }
        try {
            SpringApplication.run(MainApplication.class, args);
        } catch (Throwable e) {
            log.error("main.", e);
        }
    }
}