package com.htdong.dal.config;

import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;

@Service
public class SystemConfig {
    private static final String LOCAL_PROPERTIES = "/root/conf/application.properties";

    @PostConstruct
    public void init() {

    }
}