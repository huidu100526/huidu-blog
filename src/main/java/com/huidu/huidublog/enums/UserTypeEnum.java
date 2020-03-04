package com.huidu.huidublog.enums;

import lombok.Getter;

/**
 * @auther huidu
 * @create 2020/2/28 16:46
 * @Description: 用户类型
 */
@Getter
public enum UserTypeEnum {
    ADMIN(1, "管理员"),
    USER(2, "普通用户");

    private Integer code;

    private String message;

    UserTypeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
