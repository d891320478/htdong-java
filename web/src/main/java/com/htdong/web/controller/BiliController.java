package com.htdong.web.controller;

import java.io.FileWriter;
import java.util.Base64;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.htdong.common.util.RsaUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * @author dht31261
 * @date 2024年5月15日 19:24:57
 */
@RestController
@RequestMapping("/bili")
@Slf4j
public class BiliController {

    @PostMapping("/updateCookie")
    public ResponseEntity<Void> updateCookie(@RequestBody String cookieStr) {
        try (FileWriter fw = new FileWriter("/root/conf/biliCookie.txt")) {
            String[] encrypts = cookieStr.split("#");
            for (String iter : encrypts) {
                String cookie = RsaUtil.decrypt(Base64.getDecoder().decode(iter), "/root/conf/rsa_private_key.pem");
                fw.write(cookie);
            }
            fw.flush();
        } catch (Throwable e) {
            log.error("", e);
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).build();
        }
        return ResponseEntity.ok(null);
    }
}