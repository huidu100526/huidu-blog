package com.huidu.huidublog.controller.admin;

import com.huidu.huidublog.entity.User;
import com.huidu.huidublog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

/**
 * @auther huidu
 * @create 2020/2/22 15:46
 * @Description: 管理后台登陆controller
 */
@Controller
@RequestMapping("/admin")
public class LoginController {
    @Autowired
    private UserService userService;

    /**
     * 跳转到登陆页面
     */
    @GetMapping
    public String toLoginPage() {
        return "admin/login";
    }

    /**
     * 登陆接口
     */
    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, HttpSession session, RedirectAttributes attributes,
                        Model model) {
        User user = userService.login(username, password);
        if (user != null) {
            user.setPassword(null);
            // 设置用户信息
            session.setAttribute("user", user);
            model.addAttribute("user", user);
            return "admin/index";
        } else {
            attributes.addFlashAttribute("message", "用户名或密码错误");
            return "redirect:/admin";
        }
    }

    /**
     * 注销接口
     */
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        // 清除用户信息
        session.removeAttribute("user");
        return "redirect:/admin";
    }
}
