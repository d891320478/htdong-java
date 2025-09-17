package com.htdong.web.controller;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.htdong.common.domain.result.ApiResult;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/upload")
public class UploadController {

    @PostMapping("/tmp")
    public ResponseEntity<ApiResult<Void>> uploadTmp(@RequestParam MultipartFile file, HttpServletRequest request)
        throws IOException {
        String filepath = "/tmp/" + file.getOriginalFilename();
        try (InputStream in = file.getInputStream(); OutputStream out = new FileOutputStream(filepath)) {
            in.transferTo(out);
        }
        return ResponseEntity.ok(ApiResult.success());
    }
}
