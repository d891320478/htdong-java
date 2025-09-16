package com.htdong.core.rpc.bilibili.service.impl;

import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;

import org.apache.dubbo.config.annotation.DubboService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.protobuf.BoolValue;
import com.google.protobuf.Empty;
import com.google.protobuf.Int64Value;
import com.htdong.client.domain.db.BiliLiveRoomDO;
import com.htdong.client.domain.db.GuardDO;
import com.htdong.common.rpc.bili.AddNewGuardRequest;
import com.htdong.common.rpc.bili.BiliRpcService;
import com.htdong.dal.mapper.BiliLiveRoomMapper;
import com.htdong.dal.mapper.GuardMapper;

import jakarta.annotation.Resource;

/**
 * @author dht31261
 * @date 2023年11月14日 下午11:51:39
 */
@DubboService(group = "bili", interfaceClass = BiliRpcService.class)
public class BiliRpcServiceImpl implements BiliRpcService {

    @Resource
    private BiliLiveRoomMapper biliLiveRoomMapper;
    @Resource
    private GuardMapper guardMapper;

    @Override
    public BoolValue roomCanUseServer(Int64Value request) {
        QueryWrapper<BiliLiveRoomDO> query = new QueryWrapper<>();
        query.eq(BiliLiveRoomDO.DB_FIELD_ROOM_ID, request.getValue());
        return BoolValue.of(biliLiveRoomMapper.selectCount(query) > 0);
    }

    @Override
    public CompletableFuture<BoolValue> roomCanUseServerAsync(Int64Value request) {
        return CompletableFuture.completedFuture(roomCanUseServer(request));
    }

    @Override
    public Empty addNewGuard(AddNewGuardRequest request) {
        QueryWrapper<GuardDO> q = new QueryWrapper<>();
        q.eq(GuardDO.DB_FIELD_BILI_UID, request.getUid());
        q.eq(GuardDO.DB_FIELD_ROOM_ID, request.getRoomId());
        GuardDO guard = guardMapper.selectOne(q);
        if (guard == null) {
            guard = new GuardDO();
            guard.setGmtCreate(LocalDateTime.now());
            guard.setGmtModified(LocalDateTime.now());
            guard.setBiliUid(request.getUid());
            guard.setBiliNickName(request.getUsername());
            guard.setRoomId(request.getRoomId());
            guard.setGuardLevel(request.getGuardLevel());
            guardMapper.insert(guard);
        } else {
            guard.setGmtModified(LocalDateTime.now());
            guard.setBiliNickName(request.getUsername());
            guard.setGuardLevel(request.getGuardLevel());
            guardMapper.updateById(guard);
        }
        return Empty.getDefaultInstance();
    }

    @Override
    public CompletableFuture<Empty> addNewGuardAsync(AddNewGuardRequest request) {
        return CompletableFuture.completedFuture(addNewGuard(request));
    }
}