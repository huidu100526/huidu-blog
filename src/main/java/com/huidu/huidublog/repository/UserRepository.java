package com.huidu.huidublog.repository;

import com.huidu.huidublog.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @auther huidu
 * @create 2020/2/22 15:50
 * @Description: 用户资源类
 */
public interface UserRepository extends JpaRepository<User, Long> {
    // 根据用户名和密码查询用户
    User findByUsernameAndPassword(String username, String password);

    // 根据用户名查询用户
    User findByUsername(String username);
}
