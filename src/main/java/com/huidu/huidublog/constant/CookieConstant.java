package com.huidu.huidublog.constant;

/**
 * @auther huidu
 * @create 2020/2/29 15:59
 * @Description: cookie常量
 */
public interface CookieConstant {
    /**
     * 存到cookie中的name
     */
    String USER_COOKIE_TOKEN = "user_cookie_token";

    /**
     * cookie过期时间24小时，单位秒
     */
    Integer EXPIRE = 60 * 60 * 24;
}
