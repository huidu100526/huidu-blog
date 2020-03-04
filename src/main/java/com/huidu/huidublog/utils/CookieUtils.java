package com.huidu.huidublog.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @auther huidu
 * @create 2020/2/29 15:57
 * @Description: cookies设置获取工具类
 */
public class CookieUtils {
    /**
     * 设置cookie
     * @param name     cookie的名
     * @param value    cookie的值
     * @param maxAge   cookie的过期时间
     */
    public static void setCookie(HttpServletResponse response, String name, String value, int maxAge) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        cookie.setMaxAge(maxAge);
        response.addCookie(cookie);
    }

    /**
     * 获取cookie
     * @param name    cookie的名
     */
    public static Cookie getCookie(HttpServletRequest request, String name) {
        Map<String, Cookie> cookieMap = readCookieMap(request);
        return cookieMap.getOrDefault(name, null);
    }

    /**
     * 将cookie封装成map
     */
    private static Map<String, Cookie> readCookieMap(HttpServletRequest request) {
        Map<String, Cookie> cookieMap = new HashMap<>();
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                cookieMap.put(cookie.getName(), cookie);
            }
        }
        return cookieMap;
    }
}
