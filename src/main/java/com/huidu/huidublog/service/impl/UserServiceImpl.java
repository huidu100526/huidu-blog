package com.huidu.huidublog.service.impl;

import com.huidu.huidublog.entity.User;
import com.huidu.huidublog.repository.UserRepository;
import com.huidu.huidublog.service.UserService;
import com.huidu.huidublog.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @auther huidu
 * @create 2020/2/22 15:49
 * @Description: 用户service实现
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public User login(String username, String password) {
        // 查询用户信息
        User user = userRepository.findByUsernameAndPassword(username, MD5Utils.md5(password));
        return user;
    }
}
