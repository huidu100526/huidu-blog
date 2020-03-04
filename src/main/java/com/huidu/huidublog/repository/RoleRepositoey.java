package com.huidu.huidublog.repository;

import com.huidu.huidublog.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @auther huidu
 * @create 2020/2/28 20:53
 * @Description: 角色资源类
 */
public interface RoleRepositoey extends JpaRepository<Role, Long> {
    // 根据角色名查询角色
    Role findByName(String name);
}
