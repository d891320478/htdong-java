package com.htdong.core.service;

import com.htdong.common.domain.result.ApiResult;

/**
 * @author dht31261
 * @date 2023年8月4日 上午9:44:49
 */
public interface ShellService {

    ApiResult<String> execute(String command);
}