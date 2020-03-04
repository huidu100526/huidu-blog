package com.huidu.huidublog.config;

import com.huidu.huidublog.interceptor.BlogInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @auther huidu
 * @create 2020/2/22 16:01
 * @Description: 项目配置类
 */
//@Configuration
public class BlogConfig implements WebMvcConfigurer {
    /**
     * 注册拦截器
     */
    @Bean
    public BlogInterceptor loginInterceptor() {
        return new BlogInterceptor();
    }

    /**
     * 配置拦截器，拦截所有admin相关路径，不拦截admin登陆
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor()).addPathPatterns("/admin/**").excludePathPatterns("/admin/login", "/admin");
    }
}
