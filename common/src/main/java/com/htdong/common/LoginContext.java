package com.htdong.common;

import java.util.HashMap;
import java.util.Map;

/**
 * @author dht31261
 * @date 2023年9月28日 下午4:01:25
 */
public class LoginContext {

    private static final String KEY_UID = "uid";

    private static final ThreadLocal<Map<String, Object>> THREAD_LOCAL = new ThreadLocal<Map<String, Object>>() {
        @Override
        protected Map<String, Object> initialValue() {
            Map<String, Object> map = new HashMap<>();
            return map;
        }
    };

    public static void clear() {
        THREAD_LOCAL.get().clear();
    }

    public static void setUid(Long uid) {
        THREAD_LOCAL.get().put(KEY_UID, uid);
    }

    public static Long getUid() {
        return (Long)THREAD_LOCAL.get().get(KEY_UID);
    }
}