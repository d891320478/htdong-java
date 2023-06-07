package com.htdong.web.controller;

import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.htdong.common.domain.result.ApiResult;
import com.htdong.core.bilibili.service.BiliService;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/test")
public class TestController {

    @DubboReference
    private BiliService biliService;

    @GetMapping("checkLive")
    public ResponseEntity<ApiResult<Boolean>> checkLive(long roomId, HttpServletRequest request) {
        ServletContext context = request.getServletContext();
        System.out.println(System.identityHashCode(context));
        return ResponseEntity.ok(biliService.startLive(roomId));
    }
}