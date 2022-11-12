package com.htdong.web.controller;

import javax.annotation.Resource;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.htdong.client.domain.result.ApiResult;
import com.htdong.core.bilibili.service.BilibiliService;

@RestController
@RequestMapping("/test")
public class TestController {

    @Resource
    private BilibiliService bilibiliService;

    @GetMapping("checkLive")
    public ResponseEntity<ApiResult<Boolean>> checkLive(long roomId) {
        return ResponseEntity.ok(bilibiliService.startLive(roomId));
    }
}