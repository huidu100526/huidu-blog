package com.huidu.huidublog.service;

import com.huidu.huidublog.entity.User;

/**
 * @auther huidu
 * @create 2020/2/22 15:48
 * @Description: 用户service
 */
public interface UserService {
    User login(String username, String password);
}
