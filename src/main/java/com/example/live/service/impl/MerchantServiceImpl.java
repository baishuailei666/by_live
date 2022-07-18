package com.example.live.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.example.live.common.BaseEnum;
import com.example.live.common.BaseResult;
import com.example.live.common.Constant;
import com.example.live.controller.query.OrderQuery;
import com.example.live.entity.*;
import com.example.live.mapper.*;
import com.example.live.mapper.ContentMapper;
import com.example.live.mapper.MerchantAuditMapper;
import com.example.live.mapper.MerchantMapper;
import com.example.live.mapper.OrderMapper;
import com.example.live.service.CommonService;
import com.example.live.service.MerchantService;
import com.example.live.util.CloudSignUtil;
import com.example.live.util.GeneralUtil;
import com.example.live.util.UserUtil;
import com.example.live.vo.MerchantOrderVO;
import com.example.live.vo.MerchantVO;
import com.example.live.vo.UserVO;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
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
    @Autowired
    private MerchantAuditMapper merchantAuditMapper;
    @Autowired
    private ContentMapper contentMapper;
    @Autowired
    private VideoMapper videoMapper;
    @Autowired
    private ContractMapper contractMapper;
    @Autowired
    private PayConfigMapper payConfigMapper;
    @Autowired
    private InvoiceMapper invoiceMapper;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private CloudSignUtil cloudSignUtil;
    @Autowired
    private CommonService commonService;


    @Override
    public BaseResult<?> getMerchantListByParams(JSONObject jo) {
        int opeUserId = UserUtil.getUser().getId();
        String shop = jo.getString("shop");
        Integer page = jo.getInteger("page");
        String mobile = jo.getString("mobile");
        String shopStatus = jo.getString("shopStatus");
        int count = merchantMapper.getMerchantListByParamsCount(opeUserId, mobile, shop, shopStatus);
        if (count == 0) {
            return new BaseResult<>();
        } else {
            List<Merchant> merchantListByParams = merchantMapper.getMerchantListByParams(opeUserId, mobile, shop, shopStatus, GeneralUtil.indexPage(page));
            return new BaseResult<>(count, merchantListByParams);
        }
    }

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
        if (loginMvo == null) {
            return new BaseResult<>(BaseEnum.No_Login);
        }
        String shop = jo.getString("shop");
        String goods = jo.getString("goods");
        String shopId = jo.getString("shopId");
        String introduce = jo.getString("introduce");

        if (StringUtils.isBlank(shopId)) {
            return new BaseResult<>(11, "店铺ID不能为空");
        }
        if (StringUtils.isBlank(goods)) {
            return new BaseResult<>(12, "商品链接不能为空");
        }
        if (StringUtils.isBlank(introduce)) {
            return new BaseResult<>(13, "商家介绍不能为空");
        }
        if (introduce.length() > 140) {
            return new BaseResult<>(14, "商家介绍最多140字");
        }

        int ex = merchantMapper.existShop(shopId);
        if (ex != 0) {
            return new BaseResult<>(15, "店铺已被认证");
        }
        // 店铺绑定
        merchantMapper.bindShop(loginMvo.getId(), shopId, shop, goods, introduce);
        // 提交审核
        merchantAuditMapper.merchantShopAudit(loginMvo.getId(), loginMvo.getOpeUser());
        // 消息通知
        contentMapper.insContent(loginMvo.getId(), loginMvo.getOpeUser(), "店铺审核,店铺ID:" + shopId, 3);
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
        if (loginMvo == null) {
            return new BaseResult<>(BaseEnum.No_Login);
        }
        String shop = jo.getString("shop");
        String goods = jo.getString("goods");
        String shopId = jo.getString("shopId");
        String introduce = jo.getString("introduce");

        if (StringUtils.isBlank(shopId)) {
            return new BaseResult<>(15, "店铺ID不能为空");
        }
        if (StringUtils.isBlank(goods)) {
            return new BaseResult<>(11, "商品链接不能为空");
        }
        if (StringUtils.isBlank(introduce)) {
            return new BaseResult<>(12, "商家介绍不能为空");
        }
        if (introduce.length() > 140) {
            return new BaseResult<>(13, "商家介绍最多140字");
        }

        int ex = merchantMapper.existShop(shopId);
        if (ex != 0) {
            return new BaseResult<>(14, "店铺已被认证");
        }
        // 店铺修改
        merchantMapper.modifyShop(loginMvo.getId(), shopId, shop, goods, introduce);
        // 提交审核
        merchantAuditMapper.merchantShopAudit(loginMvo.getId(), loginMvo.getOpeUser());
        // 消息通知
        contentMapper.insContent(loginMvo.getId(), loginMvo.getOpeUser(), "店铺审核,店铺ID:" + shopId, 3);
        return new BaseResult<>();
    }

    @Override
    public BaseResult<?> merchantShopDel(String shopId) {
        int merchantId = UserUtil.getMerchantId();
        int shop_merchant_id = merchantMapper.shopMerchant(shopId);
        if (merchantId != shop_merchant_id) {
            return new BaseResult<>(16, "删除失败,店铺ID不是当前商户");
        }
        merchantMapper.modifyShop(merchantId, null, null, null, null);
        return new BaseResult<>();
    }

    @Override
    public BaseResult<?> videoCentre(Integer type) {
        List<Video> data = videoMapper.videoList(type);
        return new BaseResult<>(data.size(), data);
    }

    @Override
    public BaseResult<?> videoPlay(Integer id) {
        String path = videoMapper.video1(id);
        return new BaseResult<>();
    }

    @Override
    public BaseResult<?> merchantContractCreate(Contract contract) {
        MerchantVO mvo = UserUtil.getMerchant();

        contract.setMerchantId(mvo.getId());
        contract.setOpeUser(mvo.getOpeUser());
        contractMapper.insContract(contract);
        return new BaseResult<>();
    }

    @Override
    public BaseResult<?> merchantContractModify(Contract contract) {
        contractMapper.modifyContract(contract);
        return new BaseResult<>();
    }

    @Override
    public BaseResult<?> merchantInvoiceCreate(Invoice invoice) {
        MerchantVO mvo = UserUtil.getMerchant();
        invoice.setMerchantId(mvo.getId());
        invoice.setOpeUser(mvo.getOpeUser());
        invoiceMapper.insInvoice(invoice);
        return new BaseResult<>();
    }

    @Override
    public BaseResult<?> paySuccessCheck(String orderNo) {
        Order order = orderMapper.getOrderByNo(orderNo);
        if (order != null && Constant.pay_success.equals(order.getStatus())) {
            return new BaseResult<>("支付成功");
        }
        return new BaseResult<>(12, "支付验证");
    }

    @Override
    public BaseResult<?> merchantSignCreate(Integer type) {
        MerchantVO mvo = UserUtil.getMerchant();
        // type 0-企业、1-个人
        if (type == null) {
            return new BaseResult<>(13, "参数无效");
        }
        if (type != 1 && type != 0) {
            return new BaseResult<>(14, "参数错误");
        }

        Contract contract = contractMapper.getContract2(mvo.getId());
        if (contract == null) {
            return new BaseResult<>(12, "暂无合同内容");
        }
        JSONObject jo = new JSONObject();
        jo.put("type", type);
        jo.put("mid", mvo.getId());
        jo.put("cid", contract.getId());
        jo.put("tax", contract.getTax());
        jo.put("owner", contract.getOwner());
        jo.put("mobile", contract.getMobile());
        jo.put("company", contract.getCompany());

        Integer agentUser = commonService.merchantAgentUser(mvo.getOpeUser());
        PayConfig payConfig = payConfigMapper.getConfig(agentUser);
        jo.put("signSecretId", payConfig.getSignSecretId());
        jo.put("signSecretKey", payConfig.getSignSecretKey());
        jo.put("signTemplateId", payConfig.getSignTemplateId());

        // flowId、filename、documentId、previewFileUrl
        JSONObject jo2 = cloudSignUtil.signPreview(jo);
        return new BaseResult<>(jo2);
    }

    @Override
    public BaseResult<?> merchantSignAgree(String flowId) {
        MerchantVO mvo = UserUtil.getMerchant();
        if (mvo == null) {
            return new BaseResult<>(BaseEnum.No_Login);
        }
        Integer agentUser = commonService.merchantAgentUser(mvo.getOpeUser());
        PayConfig payConfig = payConfigMapper.getConfig(agentUser);
        String url = cloudSignUtil.signAgree(flowId, payConfig.getSignSecretId(), payConfig.getSignSecretKey());
        return new BaseResult<>(url);
    }

    @Override
    public BaseResult<?> merchantSignUrl(String flowId) {
        MerchantVO mvo = UserUtil.getMerchant();
        if (mvo == null) {
            return new BaseResult<>(BaseEnum.No_Login);
        }
        Integer agentUser = commonService.merchantAgentUser(mvo.getOpeUser());
        PayConfig payConfig = payConfigMapper.getConfig(agentUser);
        String url = cloudSignUtil.signUrl(payConfig.getSignSecretId(), payConfig.getSignSecretKey());
        return new BaseResult<>(url);
    }

}
