package com.huidu.huidublog.service;

import com.huidu.huidublog.entity.User;

/**
 * @auther huidu
 * @create 2020/2/22 15:48
 * @Description: 用户service
 */
public interface UserService {
    // 管理员登陆
    User findUserByLogin(String username, String password);

    // 查询用户名是否存在
    User findByUsername(String username);

    // 保存用户信息
    void saveUser(User user);
}
