package com.htdong.core.task;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.htdong.client.domain.db.GuardDO;
import com.htdong.client.domain.db.GuardHistoryDO;
import com.htdong.client.domain.dto.AllGuardDTO;
import com.htdong.common.domain.result.ApiResult;
import com.htdong.core.bilibili.service.BiliService;
import com.htdong.dal.mapper.GuardHistoryMapper;
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
    @Resource
    private GuardHistoryMapper guardHistoryMapper;

    @Async
    @Scheduled(cron = "0 30 1 * * ?")
    public void execute() {
        // 处理新舰长
        ApiResult<List<AllGuardDTO>> rlt = biliService.getAllGuard(liveRoomId);
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
        // 删除过期舰长移到历史表
        QueryWrapper<GuardDO> q = new QueryWrapper<>();
        LocalDateTime today = LocalDateTime.of(LocalDate.now(), LocalTime.MIN); 
        q.lt(GuardDO.DB_FIELD_GMT_MODIFIED, today);
        List<GuardDO> list = guardMapper.selectList(q);
        for (GuardDO iter : list) {
            GuardHistoryDO history = new GuardHistoryDO();
            history.setGmtCreate(LocalDateTime.now());
            history.setGmtModified(LocalDateTime.now());
            history.setBiliUid(iter.getBiliUid());
            history.setBiliNickName(iter.getBiliNickName());
            history.setGuardLevel(iter.getGuardLevel());
            history.setGmtExpired(LocalDate.now().minusDays(1));
            history.setRoomId(iter.getRoomId());
            guardHistoryMapper.insert(history);
            guardMapper.deleteById(iter.getId());
        }
    }
}