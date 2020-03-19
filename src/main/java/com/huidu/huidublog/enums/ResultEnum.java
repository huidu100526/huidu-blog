package com.huidu.huidublog.enums;

import lombok.Getter;

/**
 * @auther huidu
 * @create 2020/2/28 10:52
 * @Description: 返回状态码
 */
@Getter
public enum ResultEnum {
    // 注册相关
    SUCCESS_REGISTER(100, "注册成功，可以去登陆啦~"),
    USER_REPEAT(101, "该用户名已存在"),

    // 相应功能校验相关
    USER_NOT_LOGIN(102, "要先登陆才能操作噢"),
    USER_NOT_AUTHORITY(103, "用户权限不足"),
    FIAL_EMAIL_CHECK(105, "请填写正确格式的邮箱"),

    // 所有成功相关
    SUCCESS(200, "操作成功"),
    SUCCESS_UPDATE_USERINFO(201, "更新成功"),
    SUCCESS_UPDATE_PASSWORD(202, "密码修改成功，请重新登陆"),

    // 点赞相关
    BLOG_HAS_LIKE(300, "已经点赞过啦~"),
    SUCCESS_LIKE_BLOG(301, "点赞成功"),

    // 图片上传相关
    FILE_IS_NULL(400, "请重新选择图片"),
    FIAL_FILE_UPLOAD(401, "上传图片失败"),
    SUCCESS_FILE_UPLOAD(402, "头像上传成功")

    ;

    private Integer code;

    private String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
