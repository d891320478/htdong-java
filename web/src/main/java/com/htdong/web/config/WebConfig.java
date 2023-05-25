package com.htdong.web.config;

import java.util.HashSet;
import java.util.Set;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.htdong.common.filter.AccessFilter;

/**
 * @author dht31261
 * @date 2023年5月25日 下午8:30:37
 */
@Configuration
public class WebConfig {

    @Bean
    public FilterRegistrationBean<AccessFilter> logFilterRegister() {
        FilterRegistrationBean<AccessFilter> filterRegistrationBean = new FilterRegistrationBean<>();
        AccessFilter loggerFilter = new AccessFilter();
        filterRegistrationBean.setFilter(loggerFilter);
        Set<String> set = new HashSet<>();
        set.add("/*");
        filterRegistrationBean.setUrlPatterns(set);
        return filterRegistrationBean;
    }
}