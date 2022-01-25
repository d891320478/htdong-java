package com.htdong.core.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.htdong.client.util.ConfInitUtil;

@Configuration
public class CoreConfig {

    @Bean
    public ConfInitUtil confInitUtil(@Value("${local.conf.file}") String configFilePAth) {
        ConfInitUtil util = new ConfInitUtil();
        util.setConfFilePath(configFilePAth);
        return util;
    }
}