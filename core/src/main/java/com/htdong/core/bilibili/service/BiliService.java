package com.htdong.core.bilibili.service;

import com.htdong.client.domain.result.ApiResult;

public interface BiliService {
    ApiResult<Boolean> startLive(long roomId);
}