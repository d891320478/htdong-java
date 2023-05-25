package com.htdong.common.filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.htdong.common.util.WebUtil;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * @author dht31261
 * @date 2023年5月25日 下午8:20:08
 */
public class AccessFilter implements Filter {

    private final Logger logger = LoggerFactory.getLogger("access");

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void doFilter(ServletRequest srequest, ServletResponse sresponse, FilterChain chain)
        throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)srequest;
        HttpServletResponse response = (HttpServletResponse)sresponse;

        long start = System.nanoTime();
        chain.doFilter(request, response);
        long during = System.nanoTime() - start;
        int status = response.getStatus();
        logger.info(parseHttpRequest(request, during) + "\nstatus:" + status);

    }

    public static Cookie getCookie(String name, Cookie[] cookies) {
        if (StringUtils.isBlank(name))
            return null;
        if (null == cookies || cookies.length == 0)
            return null;
        for (Cookie cookie : cookies) {
            if (name.equals(cookie.getName()))
                return cookie;
        }
        return null;
    }

    private String parseHttpRequest(HttpServletRequest request, long during) {
        StringBuilder sb = new StringBuilder();
        sb.append("IP:").append(WebUtil.getClientIpAddr(request));
        sb.append("; TIME:").append((during / (1000 * 1000))).append("ms");
        sb.append("\n    URI:").append(request.getRequestURI());
        sb.append("\n    METHOD:").append(request.getMethod());
        sb.append("; UA:").append(request.getHeader("User-Agent"));
        sb.append("\n    COOKIE:").append(toCookiesString(request.getCookies()));
        sb.append("\n    HEADER:");
        Enumeration<String> header = request.getHeaderNames();
        while (header.hasMoreElements()) {
            String key = header.nextElement();
            sb.append("\n        ").append(key).append(" = ").append(request.getHeaders(key)).append("\n");
        }
        sb.append("\n    PARAMS:");
        Map<String, String[]> map = request.getParameterMap();
        for (String name : map.keySet()) {
            String[] values = map.get(name);
            sb.append("\n        ").append(name).append(" = ").append(Arrays.toString(values)).append("\n");
        }
        return sb.toString();
    }

    private String toCookiesString(Cookie[] cookies) {
        if (cookies == null) {
            return null;
        }
        if (cookies.length == 0) {
            return "[]";
        }
        StringBuilder result = new StringBuilder("[");
        for (Cookie cookie : cookies) {
            result.append(toCookieString(cookie)).append(",");
        }
        result.deleteCharAt(result.length() - 1);
        return result.append("]").toString();
    }

    private String toCookieString(Cookie cookie) {
        return cookie == null ? null : "{\"" + cookie.getName() + "\":\"" + cookie.getValue() + "\"}";
    }

    @Override
    public void destroy() {}
}