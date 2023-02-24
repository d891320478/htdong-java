package com.htdong.web.controller;

import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.htdong.client.domain.result.ApiResult;
import com.htdong.core.bilibili.service.BiliService;

@RestController
@RequestMapping("/test")
public class TestController {

    @DubboReference
    private BiliService biliService;

    @GetMapping("checkLive")
    public ResponseEntity<ApiResult<Boolean>> checkLive(long roomId) {
        return ResponseEntity.ok(biliService.startLive(roomId));
    }
}