package com.huidu.huidublog.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @auther huidu
 * @create 2020/2/22 10:25
 * @Description: 资源找不到异常
 */
@ResponseStatus(HttpStatus.NOT_FOUND) // 指定标识有资源找不到异常
public class NotFoundException extends RuntimeException {
    public NotFoundException() {
        super();
    }

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
