package com.htdong.common.util;

import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.type.TypeReference;
import com.htdong.common.domain.result.HttpResult;

/**
 * @author dht31261
 * @date 2023年5月17日 下午2:30:39
 */
public class HttpUtil {

    private static final Logger HTTP_LOG = LoggerFactory.getLogger("httplog");

    private static final Duration DEFAULT_TIMEOUT = Duration.ofSeconds(30);
    private static final HttpClient DEFAULT_CLIENT = HttpClient.newBuilder().connectTimeout(DEFAULT_TIMEOUT).build();

    public static final <T> HttpResult<T> httpGet(String url, TypeReference<T> type) {
        return httpGet(url, null, null, DEFAULT_TIMEOUT, type, DEFAULT_CLIENT);
    }

    public static final <T> HttpResult<T> httpGet(String url, Map<String, String> params, TypeReference<T> type) {
        return httpGet(url, params, null, DEFAULT_TIMEOUT, type, DEFAULT_CLIENT);
    }

    public static final <T> HttpResult<T> httpGet(String url, Map<String, String> params, Map<String, String> headers,
        Duration timeout, TypeReference<T> type, HttpClient client) {
        long start = System.currentTimeMillis();
        HttpResult<T> httpResult = new HttpResult<>();
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(url);
            if (params != null && !params.isEmpty()) {
                sb.append("?");
                for (Map.Entry<String, String> iter : params.entrySet()) {
                    sb.append("&").append(URLEncoder.encode(iter.getKey(), StandardCharsets.UTF_8)).append("=")
                        .append(URLEncoder.encode(iter.getValue(), StandardCharsets.UTF_8));
                }
            }
            HttpRequest.Builder builder = HttpRequest.newBuilder().uri(URI.create(url)).timeout(timeout);
            if (headers != null && !headers.isEmpty()) {
                for (Map.Entry<String, String> iter : headers.entrySet()) {
                    builder = builder.header(iter.getKey(), iter.getValue());
                }
            }
            HttpRequest req = builder.build();
            HttpResponse<String> response = client.send(req, HttpResponse.BodyHandlers.ofString());
            httpResult.setCode(response.statusCode());
            httpResult.setResponse(response);
            httpResult.setContent(response.body());
            HTTP_LOG.info("url = {}, code = {}, res = {}.", url, response.statusCode(), response.body());
            if (response.statusCode() == HttpURLConnection.HTTP_OK && type != null) {
                T result = JacksonUtil.getObjectMapper().readValue(response.body(), type);
                httpResult.setData(result);
            }
        } catch (Throwable e) {
            HTTP_LOG.error("url = {}", url, e);
            httpResult.setCode(HttpResult.HTTP_THROWABLE_CODE);
        } finally {
            httpResult.setCostTime(System.currentTimeMillis() - start);
        }
        return httpResult;
    }
}