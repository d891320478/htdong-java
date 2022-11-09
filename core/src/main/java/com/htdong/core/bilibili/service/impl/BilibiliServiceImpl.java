package com.htdong.core.bilibili.service.impl;

import org.springframework.stereotype.Service;

import com.htdong.client.domain.result.ApiResult;
import com.htdong.core.bilibili.service.BilibiliService;

@Service("bilibiliService")
public class BilibiliServiceImpl implements BilibiliService {

    private static final String BILIBILI_API = "https://api.live.bilibili.com/";

    @Override
    public ApiResult<Boolean> startLive(long roomId) {
        // TODO
        return null;
    }
}