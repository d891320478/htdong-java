package com.htdong.core.task;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.htdong.client.domain.db.NotStartLiveDO;
import com.htdong.common.domain.result.ApiResult;
import com.htdong.core.bilibili.service.BiliService;
import com.htdong.dal.mapper.NotStartLiveMapper;

import jakarta.annotation.Resource;

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
        if (rlt.isSuccess()) {
            if (!rlt.getData()) {
                notStartLiveMapper.insert(new NotStartLiveDO(liveRoomId, null));
            }
        } else {
            notStartLiveMapper.insert(new NotStartLiveDO(liveRoomId, "检查开播失败"));
        }
    }
}