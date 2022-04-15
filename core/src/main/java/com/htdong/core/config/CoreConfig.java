package com.htdong.core.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.htdong.client.conf.ConfigRegister;

@Configuration
public class CoreConfig {

    @Bean(initMethod = "init")
    public ConfigRegister configRegister(@Value("${local.conf.file}") String configFilePath) {
        ConfigRegister configRegister = new ConfigRegister(configFilePath);
        return configRegister;
    }
}