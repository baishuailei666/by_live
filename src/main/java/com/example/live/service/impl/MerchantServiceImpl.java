package com.example.live.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.example.live.common.BaseResult;
import com.example.live.entity.Merchant;
import com.example.live.mapper.MerchantMapper;
import com.example.live.service.MerchantService;
import com.example.live.util.GeneralUtil;
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
        int page = jo.getInteger("page");
        int size = jo.getInteger("size");
        int count = merchantMapper.getMerchantListByParamsCount(opeUserId, mobile, shop, shopStatus);
        if (count == 0) {
            return new BaseResult<>();
        } else {
            List<Merchant> merchantListByParams = merchantMapper.getMerchantListByParams(opeUserId, mobile, shop, shopStatus, GeneralUtil.indexPage(page, size), size==0?10:size);
            return new BaseResult<>(count, merchantListByParams);
        }

    }

}
