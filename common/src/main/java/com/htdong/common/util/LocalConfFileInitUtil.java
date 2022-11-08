package com.htdong.common.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.apache.commons.lang3.StringUtils;

public class LocalConfFileInitUtil {
    public static void init(String confFilePath) {
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
                System.setProperty(k, v);
            }
        } catch (FileNotFoundException e) {
        }
    }
}