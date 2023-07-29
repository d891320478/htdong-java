package com.htdong.core.bilibili.service;

import java.util.List;

import com.htdong.client.domain.dto.AllGuardDTO;
import com.htdong.common.domain.result.ApiResult;

public interface BiliService {

    ApiResult<Boolean> startLive(long roomId);

    ApiResult<List<AllGuardDTO>> getAllGuard();
}