package com.huidu.huidublog.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * @auther huidu
 * @create 2020/2/22 9:58
 * @Description: 系统全局异常处理
 */
@ControllerAdvice // 将所有Controller中出现的异常拦截到这里
@Slf4j
public class BlogExceptionHandler {
    @ExceptionHandler(Exception.class) // 有此注解才会进入此方法，标识拦截的异常
    public ModelAndView exceptionHandler(HttpServletRequest request, Exception e) throws Exception {
        log.error("Request URL: {}, Exception: {}", request.getRequestURL(), e);
        // 如果有指定异常则直接抛出给springboot进行处理，则不会跳转至自定义错误页面
        if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null) {
            throw e;
        }
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("url", request.getRequestURL());
        modelAndView.addObject("exception", e);
        modelAndView.setViewName("error/error");
        return modelAndView;
    }
}
