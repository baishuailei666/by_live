package com.example.live.common;


import com.example.live.util.MailUtil;
import lombok.extern.slf4j.Slf4j;
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
//        String ip = GeneralUtil.getIpAdd(request);
        log.error("# {}, {}, {}, Err:{}", request.getMethod(), request.getRequestURI(), null, ex.getMessage());
        if (!ex.getMessage().contains("HttpRequestMethodNotSupportedException")) {
            mailUtil.sendExceptionMailHandler(path, ex);
        }

        return new BaseResult<>(500, "服务器异常,请稍后重试!");
    }

}
