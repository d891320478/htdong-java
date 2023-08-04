package com.htdong.core.service.impl;

import java.io.IOException;

import org.springframework.stereotype.Service;

import com.htdong.core.service.ShellService;

import lombok.extern.slf4j.Slf4j;

/**
 * @author dht31261
 * @date 2023年8月4日 上午9:45:24
 */
@Slf4j
@Service("shellService")
public class ShellServiceImpl implements ShellService {

    @Override
    public void syncExecute(String command) {
        ProcessBuilder pb = new ProcessBuilder("bash", "-c", command);
        try {
            pb.start();
        } catch (IOException e) {
            log.error("{}", command, e);
        }
    }
}