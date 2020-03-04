package com.huidu.huidublog.service;

import com.huidu.huidublog.entity.Role;

import java.util.List;

/**
 * @auther huidu
 * @create 2020/2/28 20:55
 * @Description: 角色service
 */
public interface RoleService {
    // 查询角色列表
    List<Role> getListRole();

    // 根据角色名查询角色
    Role getRoleByName(String name);

    // 保存角色
    Role saveRole(Role role);

    // 修改角色信息
    Role updateRole(Long id, Role role);

    // 根据id查询角色
    Role getRole(Long id);

    // 删除角色
    void deleteRole(Long id);
}
