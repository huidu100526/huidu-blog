package com.huidu.huidublog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @auther huidu
 * @create 2020/2/24 19:24
 * @Description: 关于我相关controller
 */
@Controller
public class AboutController {
    /**
     * 跳转至关于我页面
     */
    @GetMapping("/about")
    public String about() {
        return "about";
    }
}
