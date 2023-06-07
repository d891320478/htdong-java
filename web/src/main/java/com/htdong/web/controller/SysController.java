package com.htdong.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;

@RestController
public class SysController {

    @GetMapping("/checkstatus")
    public String checkstatus(HttpServletRequest request) {
        ServletContext context = request.getServletContext();
        System.out.println(System.identityHashCode(context));
        System.out.println(context.getContextPath());
        System.out.println(request.getContextPath());
        return "success";
    }
}