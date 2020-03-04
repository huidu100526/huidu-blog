package com.huidu.huidublog.enums;

import lombok.Getter;

/**
 * @auther huidu
 * @create 2020/3/1 14:04
 * @Description: 角色
 */
@Getter
public enum UserRoleEnum {
    ROLE_ADMIN(1L, "ADMIN"),
    ROLE_USER(2L, "USER");

    private Long code;

    private String name;

    UserRoleEnum(Long code, String name) {
        this.code = code;
        this.name = name;
    }
}
