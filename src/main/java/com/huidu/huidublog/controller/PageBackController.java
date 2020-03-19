package com.huidu.huidublog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @auther huidu
 * @create 2020/3/5 10:10
 * @Description: 页面跳转相关controller
 */
@Controller
public class PageBackController {
    /**
     * 跳转至登陆页面
     */
    @GetMapping("/login")
    public String toLogin() {
        return "login";
    }

    /**
     * 跳转至注册页面
     */
    @GetMapping("/register")
    public String toRegister() {
        return "register";
    }

    /**
     * 跳转至关于我页面
     */
    @GetMapping("/aboutme")
    public String aboutme() {
        return "aboutme";
    }

    /**
     * 跳转至用户信息页面
     */
    @GetMapping("/userCenter")
    public String toUserInfo() {
        return "userCenter";
    }

    /**
     * 跳转至个人资料页面
     */
    @GetMapping("/userData")
    public String toUserData() {
        return "user/userData";
    }
}
