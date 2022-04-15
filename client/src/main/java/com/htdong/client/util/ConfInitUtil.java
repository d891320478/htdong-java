package com.htdong.client.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import org.apache.commons.lang3.StringUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ConfInitUtil {
    private static Map<String, String> CONF_MAP = null;

    public static String get(String key) {
        return getWithDefault(key, null);
    }

    public static String getWithDefault(String key, String defaultValue) {
        return CONF_MAP.getOrDefault(key, defaultValue);
    }

    public static void init(String confFilePath) {
        if (CONF_MAP != null) {
            return;
        }
        CONF_MAP = new HashMap<>();
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
                String v = s.substring(eqInx + 1).trim();
                CONF_MAP.put(k, v);
            }
        } catch (FileNotFoundException e) {
            log.error("config file not exist.");
        }
    }
}