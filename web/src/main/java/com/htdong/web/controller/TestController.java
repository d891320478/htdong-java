package com.htdong.web.controller;

import java.math.BigInteger;
import java.util.Random;

import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.htdong.client.domain.result.ApiResult;
import com.htdong.core.bilibili.service.BiliService;
import com.htdong.core.service.RpcService;

@RestController
@RequestMapping("/test")
public class TestController {

    @DubboReference
    private BiliService biliService;
    @DubboReference
    private RpcService rpcService;

    @GetMapping("checkLive")
    public ResponseEntity<ApiResult<Boolean>> checkLive(long roomId) {
        return ResponseEntity.ok(biliService.startLive(roomId));
    }

    @GetMapping("randomInt")
    public ResponseEntity<BigInteger> randomInt() {
        return ResponseEntity.ok(rpcService.test(new Random().nextLong()));
    }
}