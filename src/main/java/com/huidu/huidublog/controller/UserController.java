package com.huidu.huidublog.controller;

import com.huidu.huidublog.entity.User;
import com.huidu.huidublog.enums.ResultEnum;
import com.huidu.huidublog.service.UserService;
import com.huidu.huidublog.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;

/**
 * @auther huidu
 * @create 2020/2/27 20:38
 * @Description: 用户操作相关controller
 */
@Controller
public class UserController {
    @Autowired
    private UserService userService;

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
            userService.saveNewUser(user);
        } else {
            // 账号存在则返回错误信息
            return ResultVO.fial(ResultEnum.USER_REPEAT.getCode(), ResultEnum.USER_REPEAT.getMessage());
        }
        // 注册成功
        return ResultVO.success(ResultEnum.SUCCESS_REGISTER.getCode(), ResultEnum.SUCCESS_REGISTER.getMessage());
    }

    /**
     * 加载用户信息接口
     */
    @GetMapping("/loadUserInfo")
    @ResponseBody
    public ResultVO loadUserInfo(@AuthenticationPrincipal Principal principal) {
        // 获取用户信息
        User user = userService.findByUsername(principal.getName());
        return ResultVO.success(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMessage(), user);
    }

    /**
     * 跟换头像接口
     */
    @PostMapping("/user/upload")
    @ResponseBody
    public ResultVO uploadAvatar(MultipartFile file) {
        // 上传头像返回头像路径
        String imageUrl = userService.uploadImage(file);
        return ResultVO.success(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMessage(), imageUrl);
    }

    /**
     * 保存用户信息接口
     */
    @PostMapping("/updateUserInfo")
    @ResponseBody
    public ResultVO updateUserInfo(@AuthenticationPrincipal Principal principal, User user) {
        // 获取用户信息
        User dbuser = userService.findByUsername(principal.getName());
        userService.updateUserInfo(dbuser, user);
        return ResultVO.success(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMessage());
    }
}
