package com.example.live.contorller;

import com.alibaba.fastjson.JSONObject;
import com.example.live.common.BaseResult;
import com.example.live.service.impl.MerchantAuditServiceImpl;
import com.example.live.service.impl.MerchantServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author Chen Rui
 * @Date 2022/7/10 16:14
 * @Description 商户管理
 */
@RestController
@RequestMapping("/merchant")
public class MerchantController {

    @Autowired
    private MerchantServiceImpl merchantService;

    @Autowired
    private MerchantAuditServiceImpl merchantAuditService;

    /**
     * 查询商户列表，包含手机、店铺名、状态条件筛选
     * @param jo
     * @return
     */
    @PostMapping("/list")
    public BaseResult<?> merchants(@RequestBody JSONObject jo) {
        return merchantService.getMerchantListByParams(jo);
    }

    /**
     * 商户订单记录
     * @return
     */
    @GetMapping("/order")
    public BaseResult<?> merchantOrder() {
        return merchantService.merchantOrderList();
    }

    /**
     * 店铺审核列表
     * @return
     */
    @PostMapping("/audits")
    public BaseResult<?> audits(@RequestBody JSONObject jo){
       return merchantAuditService.audits(jo);
    }

    @PostMapping("merchantAudit")
    public BaseResult<?> merchantAudit(@RequestBody JSONObject jo){
        return merchantAuditService.merchantAudit(jo);
    }




}
