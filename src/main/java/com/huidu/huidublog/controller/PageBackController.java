package com.huidu.huidublog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @auther huidu
 * @create 2020/3/5 10:10
 * @Description: 页面跳转相关controller
 */
@Controller
public class PageBackController {
    private static final String SLASH_SYMBOL = "/";

    /**
     * 跳转至登陆页面
     */
    @GetMapping("/toLogin")
    public String toLogin() {
        return "login";
    }

    /**
     * 跳转至注册页面
     */
    @GetMapping("/toRegister")
    public String toRegister() {
        return "register";
    }

    /**
     * 跳转至关于我页面
     */
    @GetMapping("/aboutme")
    public String aboutme(HttpServletRequest request) {
        request.getSession().removeAttribute("lastUrl");
        return "about";
    }

    /**
     * 登录前尝试保存上一个页面的url
     */
    @GetMapping("/toLoginByUrl")
    @ResponseBody
    public void toLogin(HttpServletRequest request) {
        //保存跳转页面的url
        String lastUrl = request.getHeader("Referer");
        if (lastUrl != null) {
            try {
                URL url = new URL(lastUrl);
                if (!SLASH_SYMBOL.equals(url.getPath())) {
                    request.getSession().setAttribute("lastUrl", lastUrl);
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
    }
}
