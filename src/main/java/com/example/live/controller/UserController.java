package com.example.live.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.live.common.BaseResult;
import com.example.live.common.Constant;
import com.example.live.controller.query.OrderQuery;
import com.example.live.service.CommonService;
import com.example.live.service.MerchantAuditService;
import com.example.live.service.MerchantService;
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
    @Autowired
    private MerchantService merchantService;
    @Autowired
    private MerchantAuditService merchantAuditService;

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
     * 个人中心
     *
     * @return
     */
    @GetMapping("/user/info")
    public BaseResult<?> userInfo() {
        return userService.userInfo();
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
     * 新增订单（针对线下交易）
     * @param jo
     * @return
     */
    @PostMapping("/order/ins")
    public BaseResult<?> orderIns(@RequestBody JSONObject jo) {
        return userService.orderIns(jo);
    }

    /**
     * 业务员-查询商户列表，包含手机、店铺名、状态条件筛选
     *
     * @param jo
     * @return
     */
    @PostMapping("/user/merchant/list")
    public BaseResult<?> merchants(@RequestBody JSONObject jo) {
        return merchantService.getMerchantListByParams(jo);
    }

    /**
     * 业务员-商户订单列表
     *
     * @param orderQuery start-查询时间开始、end-查询时间结束、page-1、mobile-商户手机号
     *                   、shop-商户店铺、orderNo-订单号、buyType-购买类型 1-月卡、2-季卡、3-年卡
     * @return
     */
    @PostMapping("/user/merchant/order")
    public BaseResult<?> opeUserMerchantOrderList(@RequestBody OrderQuery orderQuery) {
        return userService.opeUserMerchantOrderList(orderQuery);
    }

    /**
     * 业务员-商户店铺审核列表
     *
     * @return
     */
    @PostMapping("/user/merchant/audit/list")
    public BaseResult<?> auditList(@RequestBody JSONObject jo) {
        return merchantAuditService.audits(jo);
    }

    /**
     * 业务员-商户店铺审核操作
     *
     * @param jo
     * @return
     */
    @PostMapping("/user/merchant/audit")
    public BaseResult<?> merchantAudit(@RequestBody JSONObject jo) {
        return merchantAuditService.merchantAudit(jo);
    }

    /**
     * 配置列表
     *
     * @return
     */
    @GetMapping("/config/data")
    public BaseResult<?> dataConfig() {
        return commonService.dataConfig();
    }

    /**
     * 配置修改
     *
     * @param jo
     * @return
     */
    @PostMapping("/config/data/modify")
    public BaseResult<?> dataConfigModify(@RequestBody JSONObject jo) {
        return commonService.dataConfigModify(jo);
    }

    /**
     * 单独月卡，季卡，年卡价格修改
     * @return
     */
    @PostMapping("/config/price/modify")
    public BaseResult<?> configModifyPrices(@RequestBody JSONObject jo){
        return commonService.configModifyPrices(jo);
    }

    /**
     * 月卡，季卡，年卡价格
     * @return
     */
    @GetMapping("/config/price")
    public BaseResult<?> showPrices(){
        return commonService.showPrices();
    }

}
