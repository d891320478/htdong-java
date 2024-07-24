package com.htdong.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;

@RestController
public class SysController {

    @GetMapping("/checkstatus")
    public String checkstatus(HttpServletRequest request) {
        return "success";
    }
}