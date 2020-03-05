package com.huidu.huidublog.enums;

import lombok.Getter;

/**
 * @auther huidu
 * @create 2020/2/28 10:52
 * @Description: 返回状态码
 */
@Getter
public enum ResultEnum {
    /**
     * 100：登陆注册校验相关
     * 200：成功
     * 300：点赞相关
     */

    SUCCESS_REGISTER(100, "注册成功，可以去登陆啦~"),
    USER_REPEAT(101, "该用户名已存在"),

    USER_NOT_LOGIN(102, "请先登陆才能操作噢"),
    USER_NOT_AUTHORITY(103, "用户权限不足"),

    USER_NULL(104, "用户不存在，请先注册"),
    FIAL_LOGIN(105, "用户名或密码错误！"),

    SUCCESS(200, "成功"),

    BLOG_HAS_LIKE(300, "已经点赞过啦~"),
    SUCCESS_LIKE_BLOG(301, "点赞成功")

    ;

    private Integer code;

    private String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
