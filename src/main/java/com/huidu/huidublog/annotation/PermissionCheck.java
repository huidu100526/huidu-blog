package com.huidu.huidublog.annotation;

import java.lang.annotation.*;

/**
 * @auther huidu
 * @create 2020/3/5 10:19
 * @Description: 设置权限值注解
 */
@Documented
@Target(ElementType.METHOD) // 指定方法级别使用
@Retention(RetentionPolicy.RUNTIME)
public @interface PermissionCheck {
    String value();
}
