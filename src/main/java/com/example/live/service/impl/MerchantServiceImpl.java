package com.example.live.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.example.live.common.BaseEnum;
import com.example.live.common.BaseResult;
import com.example.live.common.Constant;
import com.example.live.entity.*;
import com.example.live.mapper.*;
import com.example.live.mapper.ContentMapper;
import com.example.live.mapper.MerchantAuditMapper;
import com.example.live.mapper.MerchantMapper;
import com.example.live.mapper.OrderMapper;
import com.example.live.service.CommonService;
import com.example.live.service.MerchantService;
import com.example.live.single.AsyncService;
import com.example.live.util.CloudSignUtil;
import com.example.live.util.GeneralUtil;
import com.example.live.util.UserUtil;
import com.example.live.vo.MerchantOrderVO;
import com.example.live.vo.MerchantVO;
import com.example.live.vo.UserVO;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    private InvoiceMapper invoiceMapper;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private CloudSignUtil cloudSignUtil;
    @Autowired
    private ContractMapper contractMapper;
    @Autowired
    private MerchantSignMapper merchantSignMapper;
    @Autowired
    private AsyncService asyncService;


    @Override
    public BaseResult<?> getMerchantListByParams(JSONObject jo) {
        UserVO userVO = UserUtil.getUser();
        if (userVO==null) {
            return new BaseResult<>(BaseEnum.No_Login);
        }
        int opeUserId = userVO.getId();
        String shop = jo.getString("shop");
        Integer page = jo.getInteger("page");
        String mobile = jo.getString("mobile");
        String shopStatus = jo.getString("shopStatus");
        int count = merchantMapper.getMerchantListByParamsCount(opeUserId, mobile, shop, shopStatus);
        if (count == 0) {
            return new BaseResult<>();
        } else {
            List<Merchant> data = merchantMapper.getMerchantListByParams(opeUserId, mobile, shop, shopStatus, GeneralUtil.indexPage(page));
            List<Integer> mids = Lists.newArrayList();
            data.forEach(m -> mids.add(m.getId()));

            Map<Integer, Order> order1Map = Maps.newHashMap();
            List<Order> orderList = orderMapper.merchantOrderList2(mids);
            Map<Integer, List<Order>> orderListMap = orderList.stream().collect(Collectors.groupingBy(Order::getMerchantId));
            orderListMap.forEach((k, v) ->{
                v.sort(Comparator.comparing(Order::getUt));
                Order order = v.get(v.size()-1);
                order1Map.put(k, order);
            });

            List<MerchantVO> voList = Lists.newLinkedList();
            data.forEach(m ->{
                MerchantVO vo = new MerchantVO();
                vo.setId(m.getId());
                vo.setShop(m.getShop());
                vo.setMobile(m.getMobile());
                vo.setOpeUser(m.getOpeUser());
                vo.setShopStatus(m.getShopStatus());
                Order order = order1Map.get(m.getId());
                int days = GeneralUtil.buyDays(order.getBuyType(), order.getUt());
                vo.setDays(days);
                vo.setCt(m.getCt());
                vo.setLt(m.getLt());
                vo.setLoginCount(m.getLoginCount());
                vo.setBuyType(Constant.buyTypeMap.get(order.getBuyType()));
            });
            return new BaseResult<>(count, voList);
        }
    }

    @Override
    public BaseResult<?> merchantInfo() {
        MerchantVO mvo = UserUtil.getMerchant();
        if (mvo==null) {
            return new BaseResult<>(BaseEnum.No_Login);
        }
        mvo.setOpeUserWx(null);
        Order order = orderMapper.getOrder1(mvo.getId());
        if (order!=null) {
            mvo.setDays(GeneralUtil.buyDays(order.getBuyType(), order.getUt()));
            mvo.setBuyType(Constant.buyTypeMap.get(order.getBuyType()));
            mvo.setVipType(order.getBuyType());
        }
        return new BaseResult<>(mvo);
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
        MerchantVO mvo = UserUtil.getMerchant();
        if (mvo == null) {
            return new BaseResult<>(BaseEnum.No_Login);
        }
        String shop = jo.getString("shop");
        String goods = jo.getString("goods");
        String shopId = jo.getString("shopId");
        String introduce = jo.getString("introduce");

        if (StringUtils.isBlank(shop)) {
            return new BaseResult<>(16, "店铺名称不能为空");
        }
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

        Integer ex = merchantMapper.existShop(shopId);
        if (ex != null) {
            return new BaseResult<>(15, "店铺已被认证");
        }
        // 店铺绑定
        merchantMapper.bindShop(mvo.getId(), shopId, shop, goods, introduce);
        // async
        asyncService.asyncAudit(mvo, "店铺认证审核,店铺ID:"+shopId);
        return new BaseResult<>();
    }

    @Override
    public BaseResult<?> merchantShop() {
        MerchantVO mvo = UserUtil.getMerchant();
        if (mvo == null) {
            return new BaseResult<>(BaseEnum.No_Login);
        }
        Merchant merchant = merchantMapper.getMerchant2(mvo.getId());
        // // 状态：待审核-0、审核通过-1、已拒绝-2
        if (merchant!=null) {
            merchant.setAuditStatus(Constant.auditStatusMap.get(GeneralUtil.parseInt(merchant.getAuditStatus())));
        }
        return new BaseResult<>(merchant);
    }

    @Override
    public BaseResult<?> merchantShopModify(JSONObject jo) {
        MerchantVO mvo = UserUtil.getMerchant();
        if (mvo == null) {
            return new BaseResult<>(BaseEnum.No_Login);
        }
        String shop = jo.getString("shop");
        String goods = jo.getString("goods");
        String shopId = jo.getString("shopId");
        String introduce = jo.getString("introduce");

        if (StringUtils.isBlank(shopId)) {
            return new BaseResult<>(15, "店铺ID不能为空");
        }
        if (StringUtils.isBlank(shop)) {
            return new BaseResult<>(16, "店铺名称不能为空");
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

        Integer ex = merchantMapper.existShop(shopId);
        if (ex != null && ex==mvo.getId()) {
            // 店铺修改
            merchantMapper.modifyShop(mvo.getId(), shopId, shop, goods, introduce);
            // async
            asyncService.asyncAudit(mvo, "店铺修改审核,店铺ID:"+shopId);
            return new BaseResult<>();
        } else {
            return new BaseResult<>(14, "店铺已被认证");
        }
    }

    @Override
    public BaseResult<?> merchantShopDel(String shopId) {
        MerchantVO mvo = UserUtil.getMerchant();
        if (mvo == null) {
            return new BaseResult<>(BaseEnum.No_Login);
        }
        int shop_merchant_id = merchantMapper.shopMerchant(shopId);
        if (mvo.getId() != shop_merchant_id) {
            return new BaseResult<>(16, "删除失败,店铺ID不是当前商户");
        }
        merchantMapper.modifyShop(mvo.getId(), null, null, null, null);
        return new BaseResult<>();
    }

    @Override
    public BaseResult<?> videoCentre(Integer type, Integer page) {
        if (type==null) {
            return new BaseResult<>(12, "参数错误");
        }
        int count = videoMapper.count(type);
        if (count==0) {
            return new BaseResult<>();
        }
        List<Video> data = videoMapper.videoList(type, GeneralUtil.indexPage(page));
        return new BaseResult<>(count, data);
    }

    @Override
    public BaseResult<?> videoPlay(Integer id) {
        MerchantVO mvo = UserUtil.getMerchant();
        if (mvo==null) {
            return new BaseResult<>(BaseEnum.No_Login);
        }

        Video video = videoMapper.getVideo(id);
        if (video==null) {
            return new BaseResult<>(12, "没有数据");
        }
        if (video.getLevel() != mvo.getVipType()) {
            return new BaseResult<>(13, "没有权限");
        }
        String val = Constant.cloud_url+video.getPath();
        return new BaseResult<>(val);
    }

    @Override
    public BaseResult<?> merchantContractContent() {
        MerchantVO mvo = UserUtil.getMerchant();
        if (mvo==null) {
            return new BaseResult<>(BaseEnum.No_Login);
        }
        MerchantSign merchantSign = merchantSignMapper.getOne(mvo.getId());
        if (merchantSign==null) {
            return new BaseResult<>();
        }
        Merchant merchant = merchantMapper.getMerchant3(mvo.getId());
        JSONObject jo = new JSONObject();
        jo.put("id", merchantSign.getId());
        jo.put("shop", merchant.getShop());
        jo.put("subject", merchantSign.getSubject());
        jo.put("person", merchantSign.getPerson());
        jo.put("mobile", merchantSign.getMobile());
        jo.put("tax", merchantSign.getTax());
        return new BaseResult<>(jo);
    }

    @Override
    public BaseResult<?> merchantContractModify(JSONObject jo) {
        MerchantVO mvo = UserUtil.getMerchant();
        String mobile = jo.getString("mobile");
        String person = jo.getString("person");
        String subject = jo.getString("subject");
        String tax = jo.getString("tax");
        Integer id = jo.getInteger("id");
        MerchantSign sign = new MerchantSign();
        sign.setMerchantId(mvo.getId());
        sign.setSubject(subject);
        sign.setMobile(mobile);
        sign.setPerson(person);
        sign.setTax(tax);

        if (id!=null) {
            sign.setId(id);
            merchantSignMapper.modifyOne(sign);
        } else {
            merchantSignMapper.insOne(sign);
        }
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
    public BaseResult<?> merchantSignCreate(Integer type, String fee, Integer buyType) {
        MerchantVO mvo = UserUtil.getMerchant();
        // type 0-企业、1-个人
        if (type == null) {
            return new BaseResult<>(13, "参数无效");
        }
        if (type != 1 && type != 0) {
            return new BaseResult<>(14, "参数错误");
        }

        MerchantSign sign = merchantSignMapper.getSignMerchant(mvo.getId());
        JSONObject jo = new JSONObject();
        jo.put("type", type);
        jo.put("mid", mvo.getId());
        jo.put("tax", sign.getTax());
        jo.put("person", sign.getPerson());
        jo.put("mobile", sign.getMobile());
        jo.put("subject", sign.getSubject());
        jo.put("fee", fee);
        jo.put("buyType", buyType);
        jo.put("shop", sign.getShop());
        jo.put("shopId", sign.getShopId());

        // flowId、filename、documentId、previewFileUrl
        JSONObject jo2 = cloudSignUtil.signPreview(jo);

        Contract contract = new Contract();
        contract.setMerchantId(mvo.getId());
        contract.setOpeUser(mvo.getOpeUser());
        contract.setBuyType(buyType);
        contract.setSignType(type);
        contract.setDocumentId(jo2.getString("documentId"));
        contract.setDocumentName(jo.getString("filename"));
        contractMapper.insContract(contract);
        return new BaseResult<>(jo2);
    }

    @Override
    public BaseResult<?> merchantSignUrl(String flowId) {
        MerchantVO mvo = UserUtil.getMerchant();
        if (mvo == null) {
            return new BaseResult<>(BaseEnum.No_Login);
        }
        String url = cloudSignUtil.signUrl(flowId);
        return new BaseResult<>(url);
    }

}
