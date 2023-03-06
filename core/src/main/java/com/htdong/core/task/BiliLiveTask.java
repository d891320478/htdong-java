package com.htdong.core.task;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.htdong.client.domain.db.NotStartLiveDO;
import com.htdong.client.domain.result.ApiResult;
import com.htdong.core.bilibili.service.BiliService;
import com.htdong.dal.mapper.NotStartLiveMapper;

@Component
public class BiliLiveTask {

    @Value("${bili.live.room.id}")
    private Long liveRoomId;

    @Resource
    private BiliService biliService;
    @Resource
    private NotStartLiveMapper notStartLiveMapper;

    @Async
    @Scheduled(cron = "0 11 9 * * ?")
    public void checkStart() {
        ApiResult<Boolean> rlt = biliService.startLive(liveRoomId);
        if (!rlt.isSuccess()) {
            for (int i = 0; i < 10 && !rlt.isSuccess(); ++i) {
                rlt = biliService.startLive(liveRoomId);
            }
        }
        if (rlt.isSuccess()) {
            if (!rlt.getData()) {
                notStartLiveMapper.insert(new NotStartLiveDO(liveRoomId));
            } else {

            }
        } else {

        }
    }
}