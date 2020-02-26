package com.huidu.huidublog.controller.admin;

import com.huidu.huidublog.VO.ResultVO;
import com.huidu.huidublog.entity.User;
import com.huidu.huidublog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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
     * 登陆成功页面跳转
     */
    @GetMapping("/index")
    public String toIndexPage(HttpSession session, Model model) {
        // 从全局获取用户信息
        User user = (User) session.getAttribute("user");
        model.addAttribute("user", user);
        return "admin/index";
    }

    /**
     * 登陆接口
     */
    @PostMapping("/login")
    @ResponseBody
    public ResultVO login(@RequestParam String username, @RequestParam String password, HttpSession session, RedirectAttributes attributes,
                          Model model) {
        User user = userService.login(username, password);
        if (user != null) {
            user.setPassword(null);
            // 设置用户信息到session中
            session.setAttribute("user", user);
//            model.addAttribute("user", user);
//            return "admin/index";
            return ResultVO.success("登陆成功", user);
        } else {
//            attributes.addFlashAttribute("message", "用户名或密码错误");
//            attributes.addFlashAttribute("username", username);
//            attributes.addFlashAttribute("password", password);
//            return "redirect:/admin";
            return ResultVO.fial("用户名或密码错误", null);
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
