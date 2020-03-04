package com.huidu.huidublog.enums;

import lombok.Getter;

/**
 * @auther huidu
 * @create 2020/2/28 10:52
 * @Description: 返回状态码
 */
@Getter
public enum ResultEnum {
    USER_NOT_LOGIN(100, "请先登陆才能操作噢"),
    SUCCESS_REGISTER(101, "注册成功，可以去登陆啦~"),
    USERNAME_NULL(102, "不存在该用户，请先注册"),
    SUCCESS(200, "成功"),
    FIAL_LOGIN(201, "用户名或密码错误！"),
    BLOG_HAS_LIKE(202, "已经点赞过啦~"),
    SUCCESS_LIKE_BLOG(203, "喜欢成功"),
    USERNAME_REPEAT(204, "该用户名已存在")
    ;

    private Integer code;

    private String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
