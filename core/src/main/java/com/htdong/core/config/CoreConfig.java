package com.htdong.core.config;

import java.time.LocalDateTime;

import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.htdong.client.conf.ConfigRegister;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class CoreConfig {

    @Bean(initMethod = "init")
    public ConfigRegister configRegister(@Value("${local.conf.file}") String configFilePath) {
        ConfigRegister configRegister = new ConfigRegister(configFilePath);
        return configRegister;
    }

    @PreDestroy
    public void preDestroy() {
        log.info("{} app stop.", LocalDateTime.now());
    }
}