package com.example.live.contorller;

import com.alibaba.fastjson.JSONObject;
import com.example.live.common.BaseResult;
import com.example.live.common.Constant;
import com.example.live.contorller.query.OrderQuery;
import com.example.live.entity.PayConfig;
import com.example.live.service.CommonService;
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
    @Autowired
    private CommonService commonService;

    @GetMapping("/index")
    public BaseResult<?> index() {
        return new BaseResult<>("This is LiveApplication!");
    }

    /**
     * 密码登录、验证码登录
     *
     * @param session 单点项目不用考虑分布式session
     * @param jo      mobile\pwd\code\source:back-管理端、merchant-商户端
     * @return BaseResult
     */
    @PostMapping("/login")
    public BaseResult<?> userLogin(HttpSession session, @RequestBody JSONObject jo) {
        return userService.userLogin(session, jo);
    }

    /**
     * 退出登录
     *
     * @param session session
     * @return BaseResult
     */
    @PostMapping("/logout")
    public BaseResult<?> logout(HttpSession session) {
        session.removeAttribute(Constant.session_user);
        return new BaseResult<>();
    }

    /**
     * 用户列表
     *
     * @param keyword 手机号筛选
     * @param page    1
     * @return
     */
    @GetMapping("/user")
    public BaseResult<?> userList(@RequestParam("keyword") String keyword, @RequestParam("page") Integer page) {
        return userService.userList(keyword, page);
    }

    /**
     * 用户创建
     *
     * @param jo 账号、等级、备注
     * @return
     */
    @PostMapping("/user/create")
    public BaseResult<?> userCreate(@RequestBody JSONObject jo) {
        return userService.userCreate(jo);
    }

    /**
     * 用户删除
     *
     * @param id
     * @return
     */
    @GetMapping("/user/del")
    public BaseResult<?> userDel(@RequestParam("id") Integer id) {
        return userService.userDel(id);
    }

    /**
     * 订单列表
     *
     * @param query
     * @return
     */
    @GetMapping("/order")
    public BaseResult<?> orderList(OrderQuery query) {
        return userService.orderList(query);
    }

    /**
     * 配置列表
     *
     * @param mobile 手机号
     * @return
     */
    @GetMapping("/config/data")
    public BaseResult<?> dataConfig(@RequestParam("mobile") String mobile) {
        return commonService.dataConfig(mobile);
    }

    /**
     * 配置新增、修改
     *
     * @param jo
     * @return
     */
    @GetMapping("/config/data/modify")
    public BaseResult<?> dataConfigModify(JSONObject jo) {
        return commonService.dataConfigModify(jo);
    }

    /**
     * 支付配置列表
     *
     * @param mobile 手机号
     * @return
     */
    @GetMapping("/config/pay")
    public BaseResult<?> payConfig(@RequestParam("mobile") String mobile) {
        return commonService.payConfig(mobile);
    }

    /**
     * 支付配置新增
     *
     * @param payConfig
     * @return
     */
    @GetMapping("/config/pay/ins")
    public BaseResult<?> payConfigIns(PayConfig payConfig) {
        return commonService.payConfigIns(payConfig);
    }

    /**
     * 支付配置删除
     *
     * @param id id
     * @return
     */
    @GetMapping("/config/pay/del")
    public BaseResult<?> payConfigDel(@RequestParam("id") Integer id) {
        return commonService.payConfigDel(id);
    }


}
