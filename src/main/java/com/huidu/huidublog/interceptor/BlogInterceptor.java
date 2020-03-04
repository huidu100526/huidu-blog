package com.huidu.huidublog.interceptor;

import com.huidu.huidublog.constant.CookieConstant;
import com.huidu.huidublog.constant.RedisConstant;
import com.huidu.huidublog.entity.User;
import com.huidu.huidublog.utils.CookieUtils;
import com.huidu.huidublog.utils.RedisOperator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @auther huidu
 * @create 2020/2/22 15:58
 * @Description: 登陆拦截器
 */
@Slf4j
public class BlogInterceptor extends HandlerInterceptorAdapter {
    @Autowired
    private RedisOperator redis;

    /**
     * 拦截请求
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 获取用户信息
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            log.info("【登录校验】用户未登陆");
            response.sendRedirect("/admin");
            return false;
        }
        // 从cookie中获取cookie信息
        Cookie userCookie = CookieUtils.getCookie(request, CookieConstant.USER_COOKIE_TOKEN);
        // 没有信息说明cookie过期或者未登陆，跳转去登陆
        if (userCookie == null) {
            log.error("【登录校验】Cookie中查不到user_token");
            response.sendRedirect("/admin");
            return false;
        }
        // 去redis中查询唯一token缓存
        String userRedisToken = this.redis.get(RedisConstant.USER_REDIS_TOKEN + ":" + user.getUsername());
        // 没有信息说明redis中数据过期或者未登陆，跳转去登陆
        if (StringUtils.isEmpty(userRedisToken)) {
            log.error("【登陆校验】Redis中查询不到token");
            response.sendRedirect("/admin");
            return false;
        }
        // 对比唯一token是否相等
        String userCookieToken = userCookie.getValue();
        // 两个token不一致说明cookie中token被修改，跳转去登陆
        if (!userRedisToken.equals(userCookieToken)) {
            log.error("【登陆校验】cookie信息被修改");
            response.sendRedirect("/admin");
            return false;
        }
        return true;
    }
}
