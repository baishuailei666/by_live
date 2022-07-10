package com.example.live.service.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.example.live.entity.User;
import com.example.live.service.UserService;
import com.example.live.util.UserUtil;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


/**
 * 拦截器逻辑处理
 *
 * @author baishuailei@zhejianglab.com
 * @date 2022/7/8 23:16
 */
@Component
public class SysUserInterceptor extends HandlerInterceptorAdapter {

    private final UserService userService;

    public SysUserInterceptor(UserService userService) {
        this.userService = userService;
    }

    // 如果设置为false，被请求时，拦截器执行到此处将不会继续操作；
    // 如果设置为true，请求将继续执行后面的操作
    @Override
    public final boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws IOException {
        User user = UserUtil.getUser();
        // 请求路径
        String path = request.getServletPath();
        System.out.println("user:" + user);
        System.out.println("path:" + path);

//        if (user == null) {
//            handleResponse(request, response, 1001, "用户不存在, 请重新登录!");
//            return false;
//        }
        return true;
    }

    // 返回response
    private void handleResponse(HttpServletRequest request, HttpServletResponse response, int code, Object object) throws IOException {
        response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Cache-Control", "no-cache");
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter writer = response.getWriter();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status", 200);
        jsonObject.put("code", code);
        jsonObject.put("data", null);
        jsonObject.put("msg", object);

        writer.write(jsonObject.toJSONString());
    }

}
