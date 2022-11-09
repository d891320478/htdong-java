package com.htdong.core.bilibili.service;

import com.htdong.client.domain.result.ApiResult;

public interface BilibiliService {
    ApiResult<Boolean> startLive(long roomId);
}