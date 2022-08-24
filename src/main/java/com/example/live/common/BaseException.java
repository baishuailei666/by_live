package com.example.live.common;


import com.example.live.util.MailUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;


/**
 * 全局异常处理
 */
@Slf4j
@RestControllerAdvice
public class BaseException {

    @Resource
    private MailUtil mailUtil;

    @ExceptionHandler(Exception.class)
    protected BaseResult<?> handlerException(HttpServletRequest request, Exception ex) {
        ex.printStackTrace();
        String path = request.getServletPath();
        log.error("# {} {}, 发生业务异常, 原因:{}", request.getMethod(), request.getRequestURI(), ex.getMessage());
        mailUtil.sendExceptionMailHandler(path, ex);

        return new BaseResult<>(500, "服务器异常");
    }

}
