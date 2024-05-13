package com.htdong.core.service.impl;

import java.io.InputStream;

import org.springframework.stereotype.Service;

import com.htdong.common.domain.result.ApiResult;
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
    public ApiResult<String> execute(String command) {
        ProcessBuilder pb = new ProcessBuilder("bash", "-c", command);
        pb.redirectErrorStream(true);
        Process process = null;
        try {
            process = pb.start();
            String rlt;
            try (InputStream in = process.getInputStream()) {
                rlt = new String(in.readAllBytes());
            }
            int cmdCode;
            if ((cmdCode = process.waitFor()) != 0) {
                log.error("command = {}\nresultCode = {}\nout = {}", command, cmdCode, rlt);
                return ApiResult.fail(cmdCode, "执行命令失败。exit code = " + cmdCode);
            } else {
                log.info("run cmd = {}\nrlt = {}", command, rlt);
                return ApiResult.success(rlt);
            }
        } catch (Throwable e) {
            log.error("execute exception. command = {}", command, e);
            return ApiResult.fail(555, "执行命令失败");
        } finally {
            if (process != null) {
                process.destroy();
            }
        }
    }
}