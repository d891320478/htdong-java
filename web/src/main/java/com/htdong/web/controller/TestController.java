package com.htdong.web.controller;

import javax.annotation.Resource;

import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.htdong.client.domain.result.ApiResult;
import com.htdong.core.bilibili.service.BiliService;

@RestController
@RequestMapping("/test")
public class TestController {

    @Resource
    private BiliService biliService;

    @GetMapping("checkLive")
    public ResponseEntity<ApiResult<Boolean>> checkLive(long roomId) {
        ApplicationConfig application = new ApplicationConfig();
        application.setName("demo-consumer");
        RegistryConfig registry = new RegistryConfig();
        registry.setAddress("zookeeper://127.0.0.1:2181");
        ReferenceConfig<BiliService> reference = new ReferenceConfig<BiliService>();
        reference.setApplication(application);
        reference.setRegistry(registry); // 多个注册中心可以用setRegistries()
        reference.setInterface(BiliService.class);
        reference.setGroup("bili");
        BiliService service = reference.get();
        return ResponseEntity.ok(service.startLive(roomId));
    }
}