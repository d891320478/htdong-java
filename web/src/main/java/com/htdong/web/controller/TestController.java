package com.htdong.web.controller;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.htdong.client.domain.dto.AllGuardDTO;
import com.htdong.client.domain.enums.GuardLevelEnum;
import com.htdong.common.domain.result.ApiResult;
import com.htdong.core.bilibili.service.BiliService;
import com.htdong.core.task.BiliGuardTask;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/test")
public class TestController {

    @Resource
    private BiliService biliService;
    @Resource
    private BiliGuardTask biliGuardTask;
    @Resource
    private KafkaTemplate<String, String> kafkaTemplate;
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Value("#{${pokeball.downloadFilePath}}")
    private Map<String, String> downloadFilePath;

    @GetMapping("redis")
    public Object redis() {
        return downloadFilePath;
    }

    @GetMapping("checkLive")
    public ResponseEntity<ApiResult<Boolean>> checkLive(@RequestParam long roomId, HttpServletRequest request) {
        return ResponseEntity.ok(biliService.startLive(roomId));
    }

    @GetMapping("getGuardList")
    public ResponseEntity<String> getGuardList(@RequestParam long roomId) {
        ApiResult<List<AllGuardDTO>> rlt = biliService.getAllGuard(roomId);
        StringBuilder sb = new StringBuilder();
        for (AllGuardDTO iter : rlt.getData()) {
            sb.append(iter.getUid()).append(" ").append(iter.getUserName()).append(" ")
                .append(GuardLevelEnum.getById(iter.getGuardLevel()).getName()).append("<br/>");
        }
        return ResponseEntity.ok(sb.toString());
    }

    @GetMapping("test")
    public Object test() {
        biliGuardTask.execute();
        return "success";
    }

    @GetMapping("sendMsg")
    public String sendMsg() {
        CompletableFuture<SendResult<String, String>> future = kafkaTemplate.send("test000", "test000", "test000");
        future.exceptionally(e -> {
            log.error("sendMsg.", e);
            e.printStackTrace();
            throw new RuntimeException(e);
        });
        return "success";
    }
}