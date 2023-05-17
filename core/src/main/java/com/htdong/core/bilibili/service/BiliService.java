package com.htdong.core.bilibili.service;

import com.htdong.common.domain.result.ApiResult;

public interface BiliService {
    ApiResult<Boolean> startLive(long roomId);
}