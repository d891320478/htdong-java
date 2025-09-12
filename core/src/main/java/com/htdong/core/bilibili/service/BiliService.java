package com.htdong.core.bilibili.service;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

import com.htdong.client.domain.dto.AllGuardDTO;
import com.htdong.common.domain.result.ApiResult;

public interface BiliService {

    ApiResult<Boolean> startLive(long roomId);

    ApiResult<List<AllGuardDTO>> getAllGuard(long roomId);
    
    List<Pair<Long, String>> getGuardByDate(long roomId, LocalDateTime ldt);
}