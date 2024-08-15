package com.htdong.web.controller;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.htdong.client.domain.db.GuardDO;
import com.htdong.client.domain.dto.AllGuardDTO;
import com.htdong.client.domain.enums.GuardLevelEnum;
import com.htdong.common.domain.result.ApiResult;
import com.htdong.core.bilibili.service.BiliService;
import com.htdong.core.task.BiliGuardTask;
import com.htdong.dal.mapper.GuardMapper;

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
    private GuardMapper guardMapper;

    @GetMapping("checkLive")
    public ResponseEntity<ApiResult<Boolean>> checkLive(long roomId, HttpServletRequest request) {
        return ResponseEntity.ok(biliService.startLive(roomId));
    }

    @GetMapping("getGuardList")
    public ResponseEntity<String> getGuardList(@RequestParam("roomId") long roomId) {
        ApiResult<List<AllGuardDTO>> rlt = biliService.getAllGuard(roomId);
        StringBuilder sb = new StringBuilder();
        for (AllGuardDTO iter : rlt.getData()) {
            sb.append(iter.getUid()).append(" ").append(iter.getUserName()).append(" ")
                .append(GuardLevelEnum.getById(iter.getGuardLevel()).getName()).append("<br/>");
        }
        return ResponseEntity.ok(sb.toString());
    }

    @GetMapping("test")
    public ResponseEntity<String> test() {
        QueryWrapper<GuardDO> qw = new QueryWrapper<>();
        qw.gt(GuardDO.DB_FIELD_ID, 0L);
        qw.and(i -> i.eq(GuardDO.DB_FIELD_GMT_CREATE, "2024-08-13 01:30:00").or().eq(GuardDO.DB_FIELD_GMT_MODIFIED,
            "2024-08-13 01:30:00"));
        guardMapper.selectList(qw);
        return ResponseEntity.ok("success");
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