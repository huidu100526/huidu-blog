package com.huidu.huidublog.controller;

import com.huidu.huidublog.vo.ResultVO;
import com.huidu.huidublog.entity.User;
import com.huidu.huidublog.enums.ResultEnum;
import com.huidu.huidublog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
            userService.saveUser(user);
        } else {
            // 账号存在则返回错误信息
            return ResultVO.fial(ResultEnum.USER_REPEAT.getCode(), ResultEnum.USER_REPEAT.getMessage());
        }
        // 注册成功
        return ResultVO.success(ResultEnum.SUCCESS_REGISTER.getCode(), ResultEnum.SUCCESS_REGISTER.getMessage());
    }
}
