package com.htdong.web.common.advice;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.htdong.client.domain.result.ApiResult;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class ExceptionHandlerAdvice {

    @ExceptionHandler(value = Exception.class)
    public ApiResult<?> globalExceptionHandler(HttpServletRequest request, Exception e) {
        String uri = request.getRequestURI();
        log.error("globalExceptionHandler - uri:{},ex:", uri, e);
        return ApiResult.fail(500, "系统错误！");
    }
}