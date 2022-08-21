package com.example.live.common;


import com.example.live.util.MailUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.annotation.Resource;


/**
 * 全局异常处理
 */
@Slf4j
@RestControllerAdvice
public class BaseException {

    @Resource
    private MailUtil mailUtil;

    @ExceptionHandler(Exception.class)
    protected BaseResult<?> handlerException(Exception ex) {
        ex.printStackTrace();
        mailUtil.sendExceptionMailHandler(ex);

        return new BaseResult<>(500, "服务器异常");
    }

}
