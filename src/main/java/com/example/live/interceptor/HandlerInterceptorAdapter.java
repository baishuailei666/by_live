package com.example.live.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 定义拦截器
 *
 * @author baishuailei@zhejianglab.com
 * @date 2022/7/8 23:16
 */
public abstract class HandlerInterceptorAdapter implements HandlerInterceptor {

    // 在业务处理器处理请求之前被调用
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return true;
    }

}
