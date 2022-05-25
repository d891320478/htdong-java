package com.htdong.web.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.htdong.client.domain.result.ApiResult;

@RestController
@RequestMapping("/shortUrl")
public class ShortUrlController {

    @GetMapping("/${path}")
    public void shortUrl(@PathVariable("path") String path, HttpServletResponse response) {

    }

    @GetMapping("/create")
    public ApiResult<Void> create(String url) {

    }
}
