package com.htdong.web.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
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
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/test")
public class TestController {

    @Resource
    private BiliService biliService;
    @Resource
    private BiliGuardTask biliGuardTask;

    @GetMapping("checkLive")
    public ResponseEntity<ApiResult<Boolean>> checkLive(long roomId, HttpServletRequest request) {
        ServletContext context = request.getServletContext();
        System.out.println(System.identityHashCode(context));
        System.out.println(context.getContextPath());
        System.out.println(request.getContextPath());
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

    @GetMapping("guardTask")
    public ResponseEntity<String> guardTask() {
        biliGuardTask.execute();
        return ResponseEntity.ok("success");
    }
}