package com.example.live.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.example.live.common.BaseResult;
import com.example.live.common.Constant;
import com.example.live.entity.Merchant;
import com.example.live.entity.Order;
import com.example.live.mapper.MerchantMapper;
import com.example.live.mapper.OrderMapper;
import com.example.live.service.MerchantService;
import com.example.live.util.GeneralUtil;
import com.example.live.util.Page;
import com.example.live.util.UserUtil;
import com.example.live.vo.MerchantOrderVO;
import com.google.common.collect.Lists;
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
        }else {
            List<Merchant> merchantListByParams = merchantMapper.getMerchantListByParams(opeUserId, mobile, shop, shopStatus, GeneralUtil.indexPage(page),size);
            return new BaseResult<>(new Page<List>(merchantListByParams, count, size, page, GeneralUtil.indexPage(count)));
        }

    }


    @Autowired
    private OrderMapper orderMapper;

    @Override
    public BaseResult<?> merchantOrderList() {
        Integer merchantId = UserUtil.getMerchantId();
        List<Order> data = orderMapper.merchantOrderList(merchantId);
        List<MerchantOrderVO> voList = Lists.newLinkedList();
        data.forEach(o ->{
            MerchantOrderVO vo = new MerchantOrderVO();
            vo.setOrderNo(o.getOrderNo());
            vo.setMoney(o.getMoney());
            vo.setUt(o.getUt());
            vo.setBuyType(Constant.buyTypeMap.get(o.getBuyType()));
            vo.setPayType(Constant.payTypeMap.get(o.getPayType()));
            voList.add(vo);
        });
        return new BaseResult<>(voList);
    }

}
