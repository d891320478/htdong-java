package com.htdong.core.bilibili.service.impl;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.htdong.client.domain.bilibili.RoomInitDTO;
import com.htdong.client.domain.result.ApiResult;
import com.htdong.common.util.JacksonUtil;
import com.htdong.core.bilibili.service.BilibiliService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("bilibiliService")
public class BilibiliServiceImpl implements BilibiliService {

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
        return apiServiceGet(String.format(BILIBILI_LIVE_ROOM_INIT, roomId), ROOM_INIT_TYPE);
    }

    private <T> ApiResult<T> apiServiceGet(String url, TypeReference<ApiResult<T>> type) {
        url = BILIBILI_API + url;
        HttpRequest req = HttpRequest.newBuilder().uri(URI.create(url)).build();
        HttpClient client = HttpClient.newHttpClient();
        try {
            HttpResponse<String> response = client.send(req, HttpResponse.BodyHandlers.ofString());
            log.info("url = {}, code = {}, res = {}.", url, response.statusCode(), response.body());
            if (response.statusCode() == HttpStatus.OK.value()) {
                return JacksonUtil.getObjectMapper().readValue(response.body(), type);
            }
            return ApiResult.fail(response.statusCode(), "bilibili api http error.");
        } catch (Throwable e) {
            log.error("url = {}", url, e);
            return ApiResult.fail(502, "exception");
        }
    }

}