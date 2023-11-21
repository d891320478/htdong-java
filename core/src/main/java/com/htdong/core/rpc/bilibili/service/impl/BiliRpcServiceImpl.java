package com.htdong.core.rpc.bilibili.service.impl;

import java.util.concurrent.CompletableFuture;

import org.apache.dubbo.config.annotation.DubboService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.protobuf.BoolValue;
import com.google.protobuf.Int64Value;
import com.htdong.client.domain.db.BiliLiveRoomDO;
import com.htdong.common.rpc.bili.BiliRpcService;
import com.htdong.dal.mapper.BiliLiveRoomMapper;

import jakarta.annotation.Resource;

/**
 * @author dht31261
 * @date 2023年11月14日 下午11:51:39
 */
@DubboService(group="bili", interfaceClass = BiliRpcService.class)
public class BiliRpcServiceImpl implements BiliRpcService {

    @Resource
    private BiliLiveRoomMapper biliLiveRoomMapper;

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
}