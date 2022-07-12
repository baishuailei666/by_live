package com.example.live.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.example.live.common.BaseResult;
import com.example.live.common.Constant;
import com.example.live.entity.Merchant;
import com.example.live.entity.Order;
import com.example.live.mapper.ContentMapper;
import com.example.live.mapper.MerchantAuditMapper;
import com.example.live.mapper.MerchantMapper;
import com.example.live.mapper.OrderMapper;
import com.example.live.service.MerchantService;
import com.example.live.util.GeneralUtil;
import com.example.live.util.UserUtil;
import com.example.live.vo.MerchantOrderVO;
import com.example.live.vo.MerchantVO;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @author baishuailei@zhejianglab.com
 * @date 2022/7/10 13:42
 */
@Service("merchantService")
public class MerchantServiceImpl implements MerchantService {

    @Autowired
    private MerchantMapper merchantMapper;
    @Autowired
    private MerchantAuditMapper merchantAuditMapper;
    @Autowired
    private ContentMapper contentMapper;

    @Override
    public BaseResult<?> getMerchantListByParams(JSONObject jo) {
        int opeUserId = UserUtil.getUser().getId();
        String mobile = jo.getString("mobile");
        String shop = jo.getString("shop");
        String shopStatus = jo.getString("shopStatus");
        int page = jo.getInteger("page");
        int size = jo.getInteger("size");
        int count = merchantMapper.getMerchantListByParamsCount(opeUserId, mobile, shop, shopStatus);
        if (count == 0) {
            return new BaseResult<>();
        } else {
            List<Merchant> merchantListByParams = merchantMapper.getMerchantListByParams(opeUserId, mobile, shop, shopStatus, GeneralUtil.indexPage(page, size), size == 0 ? 10 : size);
            return new BaseResult<>(count, merchantListByParams);
        }
    }


    @Autowired
    private OrderMapper orderMapper;

    @Override
    public BaseResult<?> merchantOrderList() {
        Integer merchantId = UserUtil.getMerchantId();
        List<Order> data = orderMapper.merchantOrderList(merchantId);
        List<MerchantOrderVO> voList = Lists.newLinkedList();
        data.forEach(o -> {
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

    @Override
    public BaseResult<?> merchantShopBind(JSONObject jo) {
        MerchantVO loginMvo = UserUtil.getMerchant();
        if (loginMvo==null) {
            return new BaseResult<>(1, "未登录");
        }
        String shop = jo.getString("shop");
        String goods = jo.getString("goods");
        String shopId = jo.getString("shopId");
        String introduce = jo.getString("introduce");

        if (StringUtils.isBlank(shopId)) {
            return new BaseResult<>(10, "店铺ID不能为空");
        }
        if (StringUtils.isBlank(goods)) {
            return new BaseResult<>(10, "商品链接不能为空");
        }
        if (StringUtils.isBlank(introduce)) {
            return new BaseResult<>(10, "商家介绍不能为空");
        }
        if (introduce.length()>140) {
            return new BaseResult<>(10, "商家介绍最多140字");
        }

        int ex = merchantMapper.existShop(shopId);
        if (ex!=0) {
            return new BaseResult<>(10, "店铺已被认证");
        }
        // 店铺绑定
        merchantMapper.bindShop(loginMvo.getId(), shopId, shop, goods, introduce);
        // 提交审核
        merchantAuditMapper.merchantShopAudit(loginMvo.getId(), loginMvo.getOpeUser());
        // 消息通知
        contentMapper.insContent(loginMvo.getId(), loginMvo.getOpeUser(), "店铺审核,店铺ID:"+shopId,3);
        return new BaseResult<>();
    }

    @Override
    public BaseResult<?> merchantShop() {
        Integer merchantId = UserUtil.getMerchantId();
        Merchant merchant = merchantMapper.getMerchant2(merchantId);
        return new BaseResult<>(merchant);
    }

    @Override
    public BaseResult<?> merchantShopModify(JSONObject jo) {
        MerchantVO loginMvo = UserUtil.getMerchant();
        if (loginMvo==null) {
            return new BaseResult<>(1, "未登录");
        }
        String shop = jo.getString("shop");
        String goods = jo.getString("goods");
        String shopId = jo.getString("shopId");
        String introduce = jo.getString("introduce");

        if (StringUtils.isBlank(shopId)) {
            return new BaseResult<>(10, "店铺ID不能为空");
        }
        if (StringUtils.isBlank(goods)) {
            return new BaseResult<>(10, "商品链接不能为空");
        }
        if (StringUtils.isBlank(introduce)) {
            return new BaseResult<>(10, "商家介绍不能为空");
        }
        if (introduce.length()>140) {
            return new BaseResult<>(10, "商家介绍最多140字");
        }

        int ex = merchantMapper.existShop(shopId);
        if (ex!=0) {
            return new BaseResult<>(10, "店铺已被认证");
        }
        // 店铺修改
        merchantMapper.modifyShop(loginMvo.getId(), shopId, shop, goods, introduce);
        // 提交审核
        merchantAuditMapper.merchantShopAudit(loginMvo.getId(), loginMvo.getOpeUser());
        // 消息通知
        contentMapper.insContent(loginMvo.getId(), loginMvo.getOpeUser(), "店铺审核,店铺ID:"+shopId,3);
        return new BaseResult<>();
    }

    @Override
    public BaseResult<?> merchantShopDel(String shopId) {
        int merchantId = UserUtil.getMerchantId();
        int shop_merchant_id = merchantMapper.shopMerchant(shopId);
        if (merchantId!=shop_merchant_id) {
            return new BaseResult<>(10, "删除失败,店铺ID不是当前商户");
        }
        merchantMapper.modifyShop(merchantId, null, null, null, null);
        return new BaseResult<>();
    }

}
