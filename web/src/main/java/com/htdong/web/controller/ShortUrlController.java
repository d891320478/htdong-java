package com.htdong.web.controller;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.htdong.core.service.ShortUrlService;

@RestController
@RequestMapping("/shortUrl")
public class ShortUrlController {

    @Resource
    private ShortUrlService shortUrlService;

    @GetMapping("/${path}")
    public ResponseEntity<Void> shortUrl(@PathVariable("path") String path, HttpServletResponse response)
            throws IOException {
        String realUrl = shortUrlService.getUrl(path);
        if (realUrl != null) {
            response.sendRedirect(realUrl);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/create")
    public ResponseEntity<String> create(String url) {
        shortUrlService.addShortUrl(url);
        return ResponseEntity.ok().body("success");
    }
}
