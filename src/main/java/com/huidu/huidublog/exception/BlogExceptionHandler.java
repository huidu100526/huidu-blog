package com.huidu.huidublog.exception;

import com.huidu.huidublog.enums.ResultEnum;
import com.huidu.huidublog.vo.ResultVO;
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
        // 有其他异常跳转至错误页面
        modelAndView.setViewName("error/error");
        return modelAndView;
    }

    @ExceptionHandler(CheckValueException.class)
    public ResultVO checkValueException(Exception e) {
        e.printStackTrace();
        // 返回错误信息
        if (ResultEnum.FILE_IS_NULL.getMessage().equals(e.getMessage())) {
            return ResultVO.fial(ResultEnum.FILE_IS_NULL.getCode(), e.getMessage());
        } else if (ResultEnum.FILE_UPLOAD_FIAL.getMessage().equals(e.getMessage())) {
            return ResultVO.fial(ResultEnum.FILE_UPLOAD_FIAL.getCode(), e.getMessage());
        } else {
            return null;
        }
    }
}
