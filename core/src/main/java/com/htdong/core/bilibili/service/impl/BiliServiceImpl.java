package com.htdong.core.bilibili.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.htdong.client.domain.bilibili.GuardListDTO;
import com.htdong.client.domain.bilibili.RoomInitDTO;
import com.htdong.client.domain.dto.AllGuardDTO;
import com.htdong.common.domain.result.ApiResult;
import com.htdong.common.domain.result.HttpResult;
import com.htdong.common.util.HttpUtil;
import com.htdong.core.bilibili.service.BiliService;

@Service("biliService")
public class BiliServiceImpl implements BiliService {

    private static final TypeReference<ApiResult<RoomInitDTO>> ROOM_INIT_TYPE = new TypeReference<>() {};
    private static final TypeReference<ApiResult<GuardListDTO>> ROOM_GUARD_TYPE = new TypeReference<>() {};

    private static final String BILIBILI_API = "https://api.live.bilibili.com";
    private static final String BILIBILI_LIVE_ROOM_INIT = "/room/v1/Room/room_init?id=%s";
    private static final String BILIBILI_LIVE_ROOM_GUARD =
        "/xlive/app-room/v2/guardTab/topList?roomid=%d&ruid=210232&page=%d&page_size=20";

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

    @Override
    public ApiResult<List<AllGuardDTO>> getAllGuard(long roomId) {
        HttpResult<ApiResult<GuardListDTO>> httpRlt =
            HttpUtil.httpGet(BILIBILI_API + String.format(BILIBILI_LIVE_ROOM_GUARD, roomId, 1), ROOM_GUARD_TYPE);
        GuardListDTO rlt = httpRlt.getData().getData();
        List<AllGuardDTO> list = new ArrayList<>();
        Set<Long> uidSet = new HashSet<>();
        for (int i = 1; i <= rlt.getInfo().getPage(); ++i) {
            httpRlt =
                HttpUtil.httpGet(BILIBILI_API + String.format(BILIBILI_LIVE_ROOM_GUARD, roomId, i), ROOM_GUARD_TYPE);
            rlt = httpRlt.getData().getData();
            for (GuardListDTO.ListDTO iter : rlt.getTop3()) {
                if (!uidSet.contains(iter.getUid())) {
                    uidSet.add(iter.getUid());
                    list.add(new AllGuardDTO(iter.getUid(), iter.getUsername(), iter.getGuardLevel()));
                }
            }
            for (GuardListDTO.ListDTO iter : rlt.getList()) {
                if (!uidSet.contains(iter.getUid())) {
                    uidSet.add(iter.getUid());
                    list.add(new AllGuardDTO(iter.getUid(), iter.getUsername(), iter.getGuardLevel()));
                }
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
            }
        }
        return ApiResult.success(list);
    }
}