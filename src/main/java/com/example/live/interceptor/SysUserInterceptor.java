package com.example.live.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.example.live.common.Constant;
import com.example.live.service.UserService;
import com.example.live.util.UserUtil;
import com.example.live.vo.MerchantVO;
import com.example.live.vo.UserVO;
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
//        String path = request.getServletPath();
//        String from = request.getParameter("from");
//        System.out.println("path:" + path + ",from:"+from);
//        if (Constant.source_back.equals(from)) {
//            return handleUser(path, request, response);
//        } else if (Constant.source_merchant.equals(from)) {
//            return handleMerchant(path, request, response);
//        } else {
//            handleResponse(request, response, 18, "无效请求");
//            return false;
//        }
        return true;
    }
    // 未登录处理
    private boolean handleNoLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        handleResponse(request, response, 19, "登录失效");
        return false;
    }
    // 后台管理端逻辑处理
    private boolean handleUser(String path, HttpServletRequest request, HttpServletResponse response) {
        UserVO user = UserUtil.getUser();
        return true;
    }
    // 商户端逻辑处理
    private boolean handleMerchant(String path, HttpServletRequest request, HttpServletResponse response) throws IOException {
        MerchantVO mvo = UserUtil.getMerchant();
        if (path.contains("/anchor/info")) {
            // 主播详情
            if (mvo.getVipType()<1) {
                handleResponse(request, response, 10, "暂无权限");
                return false;
            }
        }
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
