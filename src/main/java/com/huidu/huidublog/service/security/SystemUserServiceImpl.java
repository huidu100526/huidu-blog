package com.huidu.huidublog.service.security;

import com.huidu.huidublog.entity.Role;
import com.huidu.huidublog.entity.User;
import com.huidu.huidublog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @auther huidu
 * @create 2020/3/1 10:22
 * @Description: security用户登陆处理，获取用户信息
 */
@Service
public class SystemUserServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 根据用户名查询用户信息
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("用户名不存在");
        }
        // 将所有角色添加到权限中（原是将所有权限添加，这里不用权限路径区分直接用角色进行权限判断）
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for(Role role : user.getRoles()){
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        // 将用户信息返回
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
    }
}
