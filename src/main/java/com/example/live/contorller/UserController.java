package com.example.live.contorller;

import com.alibaba.fastjson.JSONObject;
import com.example.live.common.BaseResult;
import com.example.live.common.Constant;
import com.example.live.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * @author baishuailei@zhejianglab.com
 * @date 2022/7/6
 */
@Slf4j
@RestController
@RequestMapping("")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/index")
    public BaseResult<?> index() {
        return new BaseResult<>("This is LiveApplication!");
    }

    /**
     * 密码登录、验证码登录
     * @param session 单点项目不用考虑分布式session
     * @param jo mobile\pwd\code\source:back-管理端、merchant-商户端
     * @return BaseResult
     */
    @PostMapping("/login")
    public BaseResult<?> userLogin(HttpSession session, @RequestBody JSONObject jo) {
        return userService.userLogin(session, jo);
    }

    /**
     * 退出登录
     * @param session session
     * @return BaseResult
     */
    @PostMapping("/logout")
    public BaseResult<?> logout(HttpSession session) {
        session.removeAttribute(Constant.session_user);
        return new BaseResult<>();
    }


}
