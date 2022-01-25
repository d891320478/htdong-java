package com.htdong.client.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.StringUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ConfInitUtil {
    private ConcurrentHashMap<String, String> CONF_MAP = null;

    private String confFilePath;

    public void setConfFilePath(String conf) {
        confFilePath = conf;
    }

    public String get(String key) {
        return getWithDefault(key, null);
    }

    public String getWithDefault(String key, String defaultValue) {
        if (CONF_MAP == null) {
            init();
        }
        return CONF_MAP.getOrDefault(key, defaultValue);
    }

    private synchronized void init() {
        if (CONF_MAP != null) {
            return;
        }
        CONF_MAP = new ConcurrentHashMap<>();
        File confFile = new File(confFilePath);
        try (Scanner in = new Scanner(confFile)) {
            while (in.hasNextLine()) {
                String s = in.nextLine();
                if (StringUtils.isBlank(s)) {
                    continue;
                }
                int eqInx = s.indexOf("=");
                if (eqInx == -1 || eqInx + 1 >= s.length() || StringUtils.isBlank(s.substring(eqInx + 1))) {
                    continue;
                }
                String k = s.substring(0, eqInx).trim();
                String v = eqInx + 1 >= s.length() ? null : s.substring(eqInx + 1).trim();
                CONF_MAP.put(k, v);
            }
        } catch (FileNotFoundException e) {
            log.error("config file not exist.");
        }
    }
}