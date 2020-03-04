package com.huidu.huidublog.service.impl;

import com.huidu.huidublog.entity.Role;
import com.huidu.huidublog.exception.NotFoundException;
import com.huidu.huidublog.repository.RoleRepositoey;
import com.huidu.huidublog.service.RoleService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @auther huidu
 * @create 2020/2/28 20:56
 * @Description: 角色service实现
 */
@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepositoey roleRepositoey;

    @Override
    public List<Role> getListRole() {
        return roleRepositoey.findAll();
    }

    @Override
    public Role getRoleByName(String name) {
        return roleRepositoey.findByName(name);
    }

    @Transactional
    @Override
    public Role saveRole(Role role) {
        return roleRepositoey.save(role);
    }

    @Transactional
    @Override
    public Role updateRole(Long id, Role role) {
        Role r = roleRepositoey.getOne(id);
        if (r == null) {
            throw new NotFoundException("不存在该角色");
        }
        BeanUtils.copyProperties(role, r);
        return roleRepositoey.save(r);
    }

    @Override
    public Role getRole(Long id) {
        return roleRepositoey.getOne(id);
    }

    @Transactional
    @Override
    public void deleteRole(Long id) {
        roleRepositoey.deleteById(id);
    }
}
