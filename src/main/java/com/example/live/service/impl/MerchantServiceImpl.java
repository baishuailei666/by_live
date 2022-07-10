package com.example.live.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.example.live.common.BaseResult;
import com.example.live.entity.Merchant;
import com.example.live.mapper.MerchantMapper;
import com.example.live.service.MerchantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author baishuailei@zhejianglab.com
 * @date 2022/7/10 13:42
 */
@Service("merchantService")
public class MerchantServiceImpl implements MerchantService {

    @Autowired
    private MerchantMapper merchantMapper;

    @Override
    public BaseResult<?> getMerchantListByParams(JSONObject jo) {
        Integer opeUserId = jo.getInteger("opeUserId");
        String mobile = jo.getString("mobile");
        String shop = jo.getString("shop");
        String shopStatus = jo.getString("shopStatus");
        List<Merchant> merchantListByParams = merchantMapper.getMerchantListByParams(opeUserId, mobile, shop, shopStatus);
        return new BaseResult(merchantListByParams);
    }

}
