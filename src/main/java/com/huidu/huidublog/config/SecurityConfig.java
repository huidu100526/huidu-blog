package com.huidu.huidublog.config;

import com.huidu.huidublog.enums.UserRoleEnum;
import com.huidu.huidublog.service.security.SystemUserServiceImpl;
import com.huidu.huidublog.utils.MD5Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @auther huidu
 * @create 2020/3/1 10:19
 * @Description: SpringSecurity配置
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true) // 开启用户识别
@Slf4j
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    /**
     * 注册 UserDetailsService
     */
    @Bean
    UserDetailsService systemUserServiceImpl() {
        return new SystemUserServiceImpl();
    }

    /**
     * 自定义认证策略
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(systemUserServiceImpl())
                // 使用MD5加密
                .passwordEncoder(new PasswordEncoder() {
                    @Override
                    public String encode(CharSequence charSequence) {
                        return MD5Utils.md5((String) charSequence);
                    }

                    // encodedPassword：数据库中的密码     charSequence：用户输入的密码
                    @Override
                    public boolean matches(CharSequence charSequence, String encodedPassword) {
                        String md5Password = MD5Utils.md5((String) charSequence);
                        log.info("【密码比对】用户输入：{}, 用户输入加密后：{}, 数据库中密码：{}", charSequence, md5Password, encodedPassword);
                        return encodedPassword.equals(md5Password);
                    }
                });
    }

    /**
     * 授权
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests() // 请求授权
                .antMatchers("/", "/index", "/types", "/tags", "/archives", "/aboutme").permitAll() // 所有人都可访问
//                .antMatchers("").hasRole(UserRoleEnum.ROLE_USER.getName()) // 普通用户
                .antMatchers("/admin/**").hasRole(UserRoleEnum.ROLE_ADMIN.getName()) // 管理员
                .and().formLogin().loginPage("/toLogin").loginProcessingUrl("/login").defaultSuccessUrl("/") // 未认证默认到登陆页面
                .and().logout().logoutUrl("/logout").logoutSuccessUrl("/"); // 注销后回到首页
        http.csrf().disable(); // 关闭csrf验证
    }
}
