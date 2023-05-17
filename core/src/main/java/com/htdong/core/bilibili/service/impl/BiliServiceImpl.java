package com.htdong.core.bilibili.service.impl;

import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.htdong.client.domain.bilibili.RoomInitDTO;
import com.htdong.common.domain.result.ApiResult;
import com.htdong.common.domain.result.HttpResult;
import com.htdong.common.util.HttpUtil;
import com.htdong.core.bilibili.service.BiliService;

@DubboService
@Service("biliService")
public class BiliServiceImpl implements BiliService {

    private static final TypeReference<ApiResult<RoomInitDTO>> ROOM_INIT_TYPE = new TypeReference<>() {};

    private static final String BILIBILI_API = "https://api.live.bilibili.com";
    private static final String BILIBILI_LIVE_ROOM_INIT = "/room/v1/Room/room_init?id=%s";

    @Override
    public ApiResult<Boolean> startLive(long roomId) {
        ApiResult<RoomInitDTO> rlt = roomInit(roomId);
        if (!rlt.isSuccess()) {
            return ApiResult.fail(rlt.getCode(), rlt.getMsg());
        }
        return ApiResult.success(rlt.getData().getLiveStatus() == RoomInitDTO.LIVING);
    }

    private ApiResult<RoomInitDTO> roomInit(long roomId) {
        HttpResult<ApiResult<RoomInitDTO>> httpRlt =
            HttpUtil.httpGet(BILIBILI_API + String.format(BILIBILI_LIVE_ROOM_INIT, roomId), ROOM_INIT_TYPE);
        if (httpRlt.isSuccess()) {
            return httpRlt.getData();
        }
        return ApiResult.fail(httpRlt.getCode(), "bilibili api http error.");
    }
}