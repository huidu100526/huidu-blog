package com.huidu.huidublog.aspect;

import com.huidu.huidublog.vo.ResultVO;
import com.huidu.huidublog.annotation.PermissionCheck;
import com.huidu.huidublog.enums.ResultEnum;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * @auther huidu
 * @create 2020/2/28 10:09
 * @Description: 拦截所有需要登录操作的controller
 */
@Aspect
@Component
@Slf4j
public class PrincipalAspect {
    private static final String ANONYMOUS_USER = "anonymousUser";

    @Pointcut("execution(* com.huidu.huidublog.controller.*.*(..))")
    public void login() {
    }

    /**
     * 在所有指定了权限校验注解的方法前验证
     */
    @Around("login() && @annotation(permissionCheck)")
    public Object principalAround(ProceedingJoinPoint joinPoint, PermissionCheck permissionCheck) throws Throwable {
        // 得到认证信息
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String loginName = authentication.getName();
        // 校验是否登陆，等于 anonymousUser 说明没有登陆
        if (loginName.equals(ANONYMOUS_USER)) {
            log.error("【登陆校验】用户未登陆");
            return ResultVO.fial(ResultEnum.USER_NOT_LOGIN.getCode(), ResultEnum.USER_NOT_LOGIN.getMessage());
        }
        // 拿到接口的权限
        String value = permissionCheck.value();
        // 接口权限拦截
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        for (GrantedAuthority authority : authorities) {
            if (authority.getAuthority().equals(value)) {
                return joinPoint.proceed();
            }
        }
        log.error("【权限校验】用户权限不足");
        return ResultVO.fial(ResultEnum.USER_NOT_AUTHORITY.getCode(), ResultEnum.USER_NOT_AUTHORITY.getMessage());
    }
}
