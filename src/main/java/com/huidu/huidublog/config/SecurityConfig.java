package com.huidu.huidublog.config;

import com.huidu.huidublog.enums.UserRoleEnum;
import com.huidu.huidublog.service.security.SystemUserServiceImpl;
import com.huidu.huidublog.utils.MD5Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;

/**
 * @auther huidu
 * @create 2020/3/1 10:19
 * @Description: SpringSecurity配置
 * WebSecurityConfigurerAdapter：自定义安全策略
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
     * 构建自定义的 DaoAuthenticationProvider
     */
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider bean = new DaoAuthenticationProvider();
        bean.setHideUserNotFoundExceptions(false);
        bean.setUserDetailsService(systemUserServiceImpl());
        bean.setPasswordEncoder(new PasswordEncoder() {
            @Override
            public String encode(CharSequence charSequence) {
                return MD5Utils.md5((String) charSequence);
            }

            @Override
            public boolean matches(CharSequence charSequence, String encodedPassword) {
                if (StringUtils.isEmpty(charSequence)) {
                    log.error("【登陆校验】用户未输入密码");
                    throw new BadCredentialsException("请输入密码!");
                } else if (!encodedPassword.equals(MD5Utils.md5((String) charSequence))) {
                    log.error("【登陆校验】用户名或密码错误");
                    throw new BadCredentialsException("用户名或密码错误!");
                } else {
                    return encodedPassword.equals(MD5Utils.md5((String) charSequence));
                }
            }
        });
        return bean;
    }

    /**
     * AuthenticationManagerBuilder：自定义认证策略
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 配置自定义校验方法
        auth.authenticationProvider(daoAuthenticationProvider());
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
                .antMatchers("/admin/**").hasRole(UserRoleEnum.ROLE_ADMIN.getName()) // 管理员
                // 未认证默认到登陆页面
                .and().formLogin().loginPage("/toLogin").loginProcessingUrl("/login").defaultSuccessUrl("/")
                // 注销后回到首页
                .and().logout().logoutUrl("/logout").logoutSuccessUrl("/");
        // 关闭csrf验证
        http.csrf().disable();
        // 避免 X-Frame-Options 响应头拒绝的问题
        http.headers().frameOptions().disable();
    }
}
