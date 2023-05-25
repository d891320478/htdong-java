package com.htdong.common.util;

import jakarta.servlet.http.HttpServletRequest;

/**
 * @author dht31261
 * @date 2023年5月25日 下午8:26:45
 */
public class WebUtil {
    private static final String[] HEADERS_ABOUT_CLIENT_IP = {"X-Forwarded-For", "Proxy-Client-IP", "WL-Proxy-Client-IP",
        "HTTP_X_FORWARDED_FOR", "HTTP_X_FORWARDED", "HTTP_X_CLUSTER_CLIENT_IP", "HTTP_CLIENT_IP", "HTTP_FORWARDED_FOR",
        "HTTP_FORWARDED", "HTTP_VIA", "REMOTE_ADDR"};

    public static String getClientIpAddr(HttpServletRequest request) {
        for (String header : HEADERS_ABOUT_CLIENT_IP) {
            String ip = request.getHeader(header);
            if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
                return ip;
            }
        }
        return request.getRemoteAddr();
    }
}
