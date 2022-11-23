package com.htdong.core.bilibili.service.impl;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.htdong.client.domain.bilibili.LoingKeyDTO;
import com.htdong.client.domain.result.ApiResult;
import com.htdong.common.util.JacksonUtil;
import com.htdong.core.bilibili.service.BiliLoginService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("biliLoginService")
public class BiliLoginServiceImpl implements BiliLoginService {

    private static final TypeReference<ApiResult<LoingKeyDTO>> LOGIN_KEY_TYPE = new TypeReference<>() {
    };

    private static final String LOGIN_SALT = "http://passport.bilibili.com/x/passport-login/web/key";

    @Value("${bili.android.appkey}")
    private String appKey;
    @Value("${bili.android.secret}")
    private String appSecret;

    @Override
    public ApiResult<?> login() {
        ApiResult<LoingKeyDTO> saltRlt = getKeyAndSalt();
        if (!saltRlt.isSuccess()) {
            return ApiResult.of(saltRlt);
        }
        return null;
    }

    private ApiResult<LoingKeyDTO> getKeyAndSalt() {
        HttpRequest req = HttpRequest.newBuilder().uri(URI.create(LOGIN_SALT)).build();
        HttpClient client = HttpClient.newHttpClient();
        try {
            HttpResponse<String> response = client.send(req, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == HttpStatus.OK.value()) {
                return JacksonUtil.getObjectMapper().readValue(response.body(), LOGIN_KEY_TYPE);
            }
            log.error("http response error. code = {}, body = {}", response.statusCode(), response.body());
        } catch (Throwable e) {
            log.error("http exception", e);
        }
        return ApiResult.fail(502, "http error");
    }
}