package com.huidu.huidublog.controller;

import com.huidu.huidublog.VO.ResultVO;
import com.huidu.huidublog.constant.CookieConstant;
import com.huidu.huidublog.constant.RedisConstant;
import com.huidu.huidublog.entity.User;
import com.huidu.huidublog.enums.ResultEnum;
import com.huidu.huidublog.service.UserService;
import com.huidu.huidublog.utils.CookieUtils;
import com.huidu.huidublog.utils.MD5Utils;
import com.huidu.huidublog.utils.RedisOperator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.UUID;

/**
 * @auther huidu
 * @create 2020/2/27 20:38
 * @Description: 登陆注册controller
 */
@Controller
public class UserLoginController {
    @Autowired
    private RedisOperator redis;

    @Autowired
    private UserService userService;

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
     * 注册接口
     */
    @PostMapping("/register")
    @ResponseBody
    public ResultVO register(User user) {
        // 查询用户名是否存在
        User dbUser = userService.findByUsername(user.getUsername());
        if (dbUser == null) {
            // 保存用户
            userService.saveUser(user);
        } else {
            // 账号存在则返回错误信息
            return ResultVO.fial(ResultEnum.USERNAME_REPEAT.getCode(), ResultEnum.USERNAME_REPEAT.getMessage());
        }
        // 注册成功
        return ResultVO.success(ResultEnum.SUCCESS_REGISTER.getCode(), ResultEnum.SUCCESS_REGISTER.getMessage());
    }

    /**
     * 登陆接口
     */
    @PostMapping("/login")
    @ResponseBody
    public ResultVO login(@RequestParam String username, @RequestParam String password,
                          HttpServletResponse response, HttpSession session) {
        // 根据用户名查询用户，判断用户是否存在
        User dbUser = userService.findByUsername(username);
        if (dbUser == null) {
            // 账号不存在
            return ResultVO.success(ResultEnum.USERNAME_NULL.getCode(), ResultEnum.USERNAME_NULL.getMessage());
        } else if (!dbUser.getUsername().equals(username)) {
            // 账号大小写错误
            return ResultVO.success(ResultEnum.FIAL_LOGIN.getCode(), ResultEnum.FIAL_LOGIN.getMessage());
        }
        // 密码比对
        String md5Password = MD5Utils.md5(password);
        if (!dbUser.getPassword().equals(md5Password)) {
            // 密码错误
            return ResultVO.success(ResultEnum.FIAL_LOGIN.getCode(), ResultEnum.FIAL_LOGIN.getMessage());
        }
        // 用户唯一token
        String userToken = UUID.randomUUID().toString().replace("-", "");
        // 将用户名设为key，唯一token设为值存入缓存中
        redis.set(RedisConstant.USER_REDIS_TOKEN + ":" + dbUser.getUsername(), userToken, RedisConstant.EXPIRE);
        // 将唯一token放入cookie中
        CookieUtils.setCookie(response, CookieConstant.USER_COOKIE_TOKEN, userToken, CookieConstant.EXPIRE);
        // 设置用户信息到session中
        session.setAttribute("user", dbUser);
        // 登陆成功
        return ResultVO.success(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMessage());
    }
}
