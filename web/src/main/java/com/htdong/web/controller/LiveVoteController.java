package com.htdong.web.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.htdong.core.service.ShellService;

import jakarta.annotation.Resource;

/**
 * @author dht31261
 * @date 2023年8月4日 上午9:42:52
 */
@RestController
@RequestMapping("/liveVote")
public class LiveVoteController {

    @Resource
    private ShellService shellService;

    @GetMapping("/startVote")
    public ResponseEntity<Void> startVote() {
        return ResponseEntity.ok(null);
    }
}