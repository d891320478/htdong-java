package com.htdong.web.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.htdong.common.util.RsaUtil;
import com.htdong.core.bilibili.service.BiliService;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;

/**
 * @author dht31261
 * @date 2024年5月15日 19:24:57
 */
@RestController
@RequestMapping("/bili")
@Slf4j
public class BiliController {

    @Value("${bili.live.room.id}")
    private Long liveRoomId;

    @Resource
    private BiliService biliService;

    @GetMapping("/giftList")
    public ResponseEntity<String> giftList() throws IOException {
        LocalDate today = LocalDate.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        StringBuilder sb = new StringBuilder();
        File f = new File("/data/biliDanMu222272/" + today.format(format) + ".log");
        if (f.exists()) {
            try (FileInputStream in = new FileInputStream(f)) {
                String dm = new String(in.readAllBytes());
                String[] ss = dm.split("\n");
                for (String s : ss) {
                    if (StringUtils.isNotBlank(s) && s.startsWith("[")) {
                        if (s.substring(20).startsWith("[SC]")) {
                            sb.append(s).append("<br/>");
                        } else if (s.substring(20).startsWith("[Gift]")) {
                            sb.append(s).append("<br/>");
                        } else if (s.substring(20).startsWith("[Guard]")) {
                            sb.append(s).append("<br/>");
                        }
                    }
                }
            }
        }
        return ResponseEntity.ok(sb.toString());
    }

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

    @GetMapping("/guardList")
    public ResponseEntity<List<Pair<Long, String>>> guardList(String date) {
        date = date + "235959";
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        LocalDateTime ldt = LocalDateTime.parse(date, format);
        List<Pair<Long, String>> list = biliService.getGuardByDate(liveRoomId, ldt);
        return ResponseEntity.ok(list);
    }
}