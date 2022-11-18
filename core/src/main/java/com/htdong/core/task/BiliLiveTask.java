package com.htdong.core.task;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.htdong.client.domain.result.ApiResult;
import com.htdong.core.bilibili.service.BilibiliService;

@Component
public class BiliLiveTask {

    @Value("${bili.live.room.id}")
    private Long liveRoomId;

    @Resource
    private BilibiliService bilibiliService;

    @Async
    @Scheduled(cron = "0 10 9 * * ?")
    public void checkStart() {
        ApiResult<Boolean> rlt = bilibiliService.startLive(liveRoomId);
        if (!rlt.isSuccess()) {

        } else if (!rlt.getData()) {

        } else {

        }
    }
}