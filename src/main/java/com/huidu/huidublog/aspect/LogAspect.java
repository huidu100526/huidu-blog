package com.huidu.huidublog.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * @auther huidu
 * @create 2020/2/22 12:35
 * @Description: 日志切面
 */
@Aspect
@Component
@Slf4j
public class LogAspect {
    /**
     * 拦截所有controller层
     */
    @Pointcut("execution(* com.huidu.huidublog.controller.*.*(..))")
    public void log(){}

    /**
     * 执行controller方法之前执行
     * 打印出请求路径、ip、类名方法名、参数信息
     */
    @Before("log()")
    public void doBefore(JoinPoint joinPoint) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            // 获取request
            HttpServletRequest request = attributes.getRequest();
            String url = request.getRequestURL().toString(); // 请求路径
            String ip = request.getRemoteAddr(); // ip
            // controller所在类名方法名
            String classMethod = joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName();
            Object[] args = joinPoint.getArgs();
            RequestLog requestLog = new RequestLog(url, ip, classMethod, args);
            log.info("【请求信息】: {}", requestLog); // 打印信息
        }
    }

    /**
     * 执行controller方法之后执行
     */
    @After("log()")
    public void doAfter() { }

    /**
     * 将controller方法返回的值打印
     */
    @AfterReturning(returning = "result",pointcut = "log()")
    public void doAfterRuturn(JoinPoint joinPoint, Object result) {
        String methodName = joinPoint.getSignature().getName();
        log.info("【" + methodName + "方法返回结果】: {}", result);
    }

    private class RequestLog {
        private String url; // 请求路径
        private String ip; // 请求ip地址
        private String classMethod; // 请求方法名
        private Object[] args; // 请求参数

        RequestLog(String url, String ip, String classMethod, Object[] args) {
            this.url = url;
            this.ip = ip;
            this.classMethod = classMethod;
            this.args = args;
        }

        @Override
        public String toString() {
            return "{" +
                    "url='" + url + '\'' +
                    ", ip='" + ip + '\'' +
                    ", classMethod='" + classMethod + '\'' +
                    ", args=" + Arrays.toString(args) +
                    '}';
        }
    }
}
