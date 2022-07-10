package com.example.live.contorller;

import com.alibaba.fastjson.JSONObject;
import com.example.live.common.BaseResult;
import com.example.live.service.impl.MerchantServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author Chen Rui
 * @Date 2022/7/10 16:14
 * @Description 商户管理
 */
@RestController("/merchant")
public class MerchantController {

    @Autowired
    private MerchantServiceImpl merchantService;

    /**
     * 查询商户列表，包含手机、店铺名、状态条件筛选
     * @param jo
     * @return
     */
    @PostMapping("/list")
    public BaseResult<?> merchants(@RequestBody JSONObject jo) {
        return merchantService.getMerchantListByParams(jo);
    }





}
