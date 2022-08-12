package com.example.live.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.example.live.common.Constant;
import com.example.live.service.UserService;
import com.example.live.util.UserUtil;
import com.example.live.vo.MerchantVO;
import com.example.live.vo.UserVO;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;


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


    // 免拦截接口
    private static final List<String> apis_none = Lists.newArrayList();
    static {
        apis_none.add("/index");
        apis_none.add("/login");
        apis_none.add("/logout");
        apis_none.add("/pay/**");
        apis_none.add("/common/code");
        apis_none.add("/common/kef");
        apis_none.add("/common/msg/**");
        apis_none.add("/common/pwd/modify");

        // 不能解决from传参的问题
        apis_none.add("/common/upload/img");
        apis_none.add("/common/upload/excel");
        apis_none.add("/common/upload/video");
    }
    // 超级管理员独有权限
    private static final List<String> apis_admin11 = Lists.newArrayList();
    static {
        apis_admin11.add("/common/upload/video");
        apis_admin11.add("/common/upload/excel");
        apis_admin11.add("/common/upload/cert");
        apis_admin11.add("/common/pwd/reset");
        apis_admin11.add("/config/data/**");
        apis_admin11.add("/config/price/**");
    }

    // 如果设置为false，被请求时，拦截器执行到此处将不会继续操作；
    // 如果设置为true，请求将继续执行后面的操作
    @Override
    public final boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws IOException {
        String path = request.getServletPath();
        String from = request.getHeader("from");

        // 免拦截接口直接请求返回
        if (apis_none.contains(path)) {
            System.out.println("## path:" + path + ", from:"+from);
            return true;
        }
        if (StringUtils.isBlank(from)) {
            from = Constant.source_back;
        }


        if (Constant.source_back.equals(from)) {
            // 后台管理端
            return handleUser(path, request, response);
        } else if (Constant.source_merchant.equals(from)) {
            // 商家客户端
            return handleMerchant(path, request, response);
        } else {
            // 未知请求
            handleResponse(request, response, 18, "无效请求");
            return false;
        }
    }

    // 后台管理端逻辑处理
    private boolean handleUser(String path, HttpServletRequest request, HttpServletResponse response) throws IOException {
        UserVO user = UserUtil.getUser();
        if (user==null) {
            return handleNoLogin(request, response);
        }
        String from = request.getHeader("from");
        System.out.println("## path:" + path + ", from:"+from +", user:"+user.getId());

        // 超级管理员独有权限
        if (apis_admin11.contains(path) && user.getLevel()!=1) {
            handleResponse(request, response, 21, "没有操作权限");
            return false;
        }
        return true;
    }
    // 商户端逻辑处理
    private boolean handleMerchant(String path, HttpServletRequest request, HttpServletResponse response) throws IOException {
        MerchantVO mvo = UserUtil.getMerchant();
        if (mvo==null) {
            return handleNoLogin(request, response);
        }
        String from = request.getHeader("from");
        System.out.println("## path:" + path + ", from:"+from +", merchant:"+mvo.getId());

        if (apis_admin11.contains(path)) {
            handleResponse(request, response, 21, "没有操作权限");
            return false;
        }
        return true;
    }
    // 未登录处理
    private boolean handleNoLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        handleResponse(request, response, 10, "登录状态已失效,请重新登录");
        return false;
    }

    // 返回response
    private void handleResponse(HttpServletRequest request, HttpServletResponse response, int code, Object object) throws IOException {
        response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setContentType("application/json; charset=utf-8");
        response.setHeader("Cache-Control", "no-cache");
        response.setCharacterEncoding("utf-8");

        PrintWriter writer = response.getWriter();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status", 200);
        jsonObject.put("code", code);
        jsonObject.put("data", null);
        jsonObject.put("msg", object);
        jsonObject.put("from", "Interceptor");

        writer.write(jsonObject.toJSONString());
    }

}
