package com.huidu.huidublog.constant;

/**
 * @auther huidu
 * @create 2020/2/29 15:55
 * @Description: redis常量
 */
public interface RedisConstant {
    /**
     * 存到redis中key的前缀
     */
    String USER_REDIS_TOKEN = "user_redis_token";

    /**
     * redis过期时间24小时，单位秒
     */
    Integer EXPIRE = 60 * 60 * 24;
}
