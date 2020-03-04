package com.huidu.huidublog.service.impl;

import com.huidu.huidublog.entity.Role;
import com.huidu.huidublog.entity.User;
import com.huidu.huidublog.enums.UserRoleEnum;
import com.huidu.huidublog.repository.RoleRepositoey;
import com.huidu.huidublog.repository.UserRepository;
import com.huidu.huidublog.service.UserService;
import com.huidu.huidublog.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @auther huidu
 * @create 2020/2/22 15:49
 * @Description: 用户service实现
 */
@Service
public class UserServiceImpl implements UserService {
    // 注入默认的头像信息
    @Value("${comment.avatar}")
    private String USER_DEFAULT_AVATAR;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepositoey roleRepositoey;

    @Override
    public User findUserByLogin(String username, String password) {
        // 查询管理员信息
        User user = userRepository.findByUsernameAndPassword(username, MD5Utils.md5(password));
        return user;
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public void saveUser(User user) {
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
        userRepository.save(user);
    }
}
