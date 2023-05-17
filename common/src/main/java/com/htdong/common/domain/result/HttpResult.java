package com.htdong.common.domain.result;

import java.net.http.HttpResponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author dht31261
 * @date 2023年5月17日 下午3:01:38
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HttpResult<T> implements CommonResult {
    
    public static final int HTTP_THROWABLE_CODE = -601;

    private int code;
    private T data;
    private HttpResponse<?> response;
    private long costTime;

    @Override
    public boolean isSuccess() {
        return code == 200;
    }
}