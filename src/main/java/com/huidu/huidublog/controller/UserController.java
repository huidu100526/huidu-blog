package com.huidu.huidublog.controller;

import com.huidu.huidublog.annotation.PermissionCheck;
import com.huidu.huidublog.entity.Role;
import com.huidu.huidublog.entity.User;
import com.huidu.huidublog.enums.ResultEnum;
import com.huidu.huidublog.enums.UserRoleEnum;
import com.huidu.huidublog.repository.RoleRepositoey;
import com.huidu.huidublog.service.UserService;
import com.huidu.huidublog.utils.MD5Utils;
import com.huidu.huidublog.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @auther huidu
 * @create 2020/2/27 20:38
 * @Description: 用户操作相关controller
 */
@RestController
public class UserController {
    // 注入默认的头像信息
    @Value("${comment.avatar}")
    private String USER_DEFAULT_AVATAR;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleRepositoey roleRepositoey;

    /**
     * 注册接口
     */
    @PostMapping("/register")
    public ResultVO register(User user) {
        // 查询用户名是否存在
        User dbUser = userService.findByUsername(user.getUsername());
        if (dbUser == null) {
            // 设置用户默认信息
            user.setPassword(MD5Utils.md5(user.getPassword())); // 设置加密密码
            user.setAvatar(USER_DEFAULT_AVATAR); // 设置用户默认头像
            user.setCreateTime(new Date()); // 注册时间
            user.setUpdateTime(new Date()); // 修改信息时间
            // 设置用户角色，默认为普通用户
            List<Role> roles = new ArrayList<>();
            roles.add(roleRepositoey.getOne(UserRoleEnum.ROLE_USER.getCode()));
            user.setRoles(roles);
            // 保存用户
            userService.saveUser(user);
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
    @PermissionCheck(value = "ROLE_USER")
    public ResultVO loadUserInfo(@AuthenticationPrincipal Principal principal) {
        // 获取用户信息
        User user = userService.findByUsername(principal.getName());
        return ResultVO.success(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMessage(), user);
    }

    /**
     * 跟换头像接口
     */
    @PostMapping("/user/upload")
    @PermissionCheck(value = "ROLE_USER")
    public ResultVO uploadAvatar(MultipartFile file) {
        // 上传头像返回头像路径
        String imageUrl = userService.uploadImage(file);
        return ResultVO.success(ResultEnum.SUCCESS_FILE_UPLOAD.getCode(), ResultEnum.SUCCESS_FILE_UPLOAD.getMessage(), imageUrl);
    }

    /**
     * 保存用户信息接口
     */
    @PostMapping("/updateUserInfo")
    @PermissionCheck(value = "ROLE_USER")
    public ResultVO updateUserInfo(@AuthenticationPrincipal Principal principal, User user) {
        // 获取用户信息
        User dbuser = userService.findByUsername(principal.getName());
        // 设置相应的新值
        dbuser.setAvatar(user.getAvatar());
        dbuser.setNickname(user.getNickname());
        // 如果邮箱有值则校验邮箱
        if (!"".equals(user.getEmail())) {
            String regex = "^([a-z0-9A-Z]+[-|.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
            if (user.getEmail().matches(regex)) {
                dbuser.setEmail(user.getEmail());
            } else {
                // 邮箱不合法
                return ResultVO.success(ResultEnum.FIAL_EMAIL_CHECK.getCode(), ResultEnum.FIAL_EMAIL_CHECK.getMessage());
            }
        }
        dbuser.setUserProfile(user.getUserProfile());
        dbuser.setUpdateTime(new Date());
        userService.saveUser(dbuser);
        return ResultVO.success(ResultEnum.SUCCESS_UPDATE_USERINFO.getCode(), ResultEnum.SUCCESS_UPDATE_USERINFO.getMessage());
    }

    /**
     * 修改密码接口
     */
    @PostMapping("/updatePassword")
    @PermissionCheck(value = "ROLE_USER")
    public ResultVO updatePassword(@AuthenticationPrincipal Principal principal, String newPassword) {
        // 获取用户信息
        User dbuser = userService.findByUsername(principal.getName());
        // 设置新密码
        dbuser.setPassword(MD5Utils.md5(newPassword));
        userService.saveUser(dbuser);
        return ResultVO.success(ResultEnum.SUCCESS_UPDATE_PASSWORD.getCode(), ResultEnum.SUCCESS_UPDATE_PASSWORD.getMessage());
    }
}
