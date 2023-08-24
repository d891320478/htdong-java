package com.htdong.core.task;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.htdong.client.domain.db.GuardDO;
import com.htdong.client.domain.dto.AllGuardDTO;
import com.htdong.common.domain.result.ApiResult;
import com.htdong.core.bilibili.service.BiliService;
import com.htdong.dal.mapper.GuardMapper;

import jakarta.annotation.Resource;

/**
 * @author dht31261
 * @date 2023年8月24日 下午2:43:53
 */
@Component
public class BiliGuardTask {

    @Value("${bili.live.room.id}")
    private Long liveRoomId;

    @Resource
    private BiliService biliService;
    @Resource
    private GuardMapper guardMapper;

    @Async
    @Scheduled(cron = "0 0 1 * * ?")
    public void execute() {
        ApiResult<List<AllGuardDTO>> rlt = biliService.getAllGuard();
        for (AllGuardDTO iter : rlt.getData()) {
            QueryWrapper<GuardDO> q = new QueryWrapper<>();
            q.eq(GuardDO.DB_FIELD_BILI_UID, iter.getUid());
            q.eq(GuardDO.DB_FIELD_ROOM_ID, liveRoomId);
            GuardDO guard = guardMapper.selectOne(q);
            if (guard == null) {
                guard = new GuardDO();
                guard.setGmtCreate(LocalDateTime.now());
                guard.setGmtModified(LocalDateTime.now());
                guard.setBiliUid(iter.getUid());
                guard.setBiliNickName(iter.getUserName());
                guard.setRoomId(liveRoomId);
                guard.setGuardLevel(iter.getGuardLevel());
                guardMapper.insert(guard);
            } else {
                guard.setGmtModified(LocalDateTime.now());
                guard.setBiliNickName(iter.getUserName());
                guard.setGuardLevel(iter.getGuardLevel());
                guardMapper.updateById(guard);
            }
        }
        QueryWrapper<GuardDO> q = new QueryWrapper<>();
        q.lt(GuardDO.DB_FIELD_GMT_MODIFIED, LocalDateTime.now().minusDays(2));
        guardMapper.delete(q);
    }
}