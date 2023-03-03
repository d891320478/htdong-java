package com.htdong.common.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.StringUtils;

public class LocalConfigUtil {

    private static ConcurrentHashMap<String, String> CONF_MAP = null;

    public static Map<String, String> getMap(String confFilePath) {
        if (CONF_MAP == null) {
            init(confFilePath);
        }
        return new HashMap<>(CONF_MAP);
    }

    private static synchronized void init(String confFilePath) {
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
            System.out.println("config file not exist.");
        }
    }
}