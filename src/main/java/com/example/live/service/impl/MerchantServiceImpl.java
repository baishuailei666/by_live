package com.example.live.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.example.live.common.BaseEnum;
import com.example.live.common.BaseResult;
import com.example.live.common.Constant;
import com.example.live.entity.*;
import com.example.live.mapper.*;
import com.example.live.service.CommonService;
import com.example.live.service.MerchantService;
import com.example.live.single.AsyncService;
import com.example.live.util.CloudSignUtil;
import com.example.live.util.GeneralUtil;
import com.example.live.util.UserUtil;
import com.example.live.vo.MerchantListVO;
import com.example.live.vo.MerchantOrderVO;
import com.example.live.vo.MerchantVO;
import com.example.live.vo.UserVO;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
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
    @Autowired
    private DataConfigMapper dataConfigMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private CommonService commonService;


    @Override
    public BaseResult<?> getMerchantListByParams(JSONObject jo) {
        UserVO u = UserUtil.getUser();
        if (u == null) {
            return new BaseResult<>(BaseEnum.No_Login);
        }
        int opeUserId = u.getId();
        String shop = jo.getString("shop");
        Integer page = jo.getInteger("page");
        String mobile = jo.getString("mobile");
        String shopStatus = jo.getString("shopStatus");

        List<Integer> opeUserIds = Lists.newArrayList();
        if (u.getLevel()!=3) {
            // 不是业务员级别
            opeUserIds = commonService.opeUserIds(u.getId());
        }
        opeUserIds.add(u.getId());

        int count = merchantMapper.getMerchantListByParamsCount(opeUserIds, mobile, shop, shopStatus);
        if (count == 0) {
            return new BaseResult<>();
        } else {
            List<Merchant> data = merchantMapper.getMerchantListByParams(opeUserIds, mobile, shop, shopStatus, GeneralUtil.indexPage(page));
            List<Integer> mids = Lists.newArrayList();
            Set<Integer> uids = Sets.newHashSet();
            data.forEach(m -> {
                mids.add(m.getId());
                uids.add(m.getOpeUser());
            });

            List<User> users = userMapper.userList2(new ArrayList<>(uids));
            Map<Integer, User> userMap = users.stream().collect(Collectors.toMap(User::getId, Function.identity(), (k1, k2) -> k2));

            List<Order> orderList = orderMapper.merchantOrderList2(mids);
            Map<Integer, List<Order>> orderListMap = orderList.stream().collect(Collectors.groupingBy(Order::getMerchantId));
            Map<Integer, Order> order1Map = Maps.newHashMap();
            orderListMap.forEach((k, v) -> {
                v.sort(Comparator.comparing(Order::getUt));
                Order order = v.get(v.size() - 1);
                order1Map.put(k, order);
            });

            List<MerchantListVO> voList = Lists.newLinkedList();
            data.forEach(m -> {
                MerchantListVO vo = new MerchantListVO();
                vo.setId(m.getId());
                vo.setCt(m.getCt());
                vo.setLt(m.getLt());
                vo.setShop(m.getShop());
                vo.setMobile(m.getMobile());
                vo.setShopStatus(m.getShopStatus());
                vo.setLoginCount(m.getLoginCount());
                Order order = order1Map.get(m.getId());
                if (order!=null) {
                    int days = GeneralUtil.buyDays(order.getBuyType(), order.getUt());
                    vo.setDays(days);
                }
                User user = userMap.get(m.getOpeUser());
                if (user != null) {
                    vo.setOpeUser(GeneralUtil.opeUserHandler(user.getId(), user.getRemark(), user.getMobile()));
                }
                voList.add(vo);
            });
            return new BaseResult<>(count, voList);
        }
    }

    @Override
    public BaseResult<?> merchantSearch() {
        UserVO uvo = UserUtil.getUser();
        if (uvo==null) {
            return new BaseResult<>(BaseEnum.No_Login);
        }
//        if (uvo.getId()!=Constant.admin_id) {
//            return new BaseResult<>(13, "没有权限");
//        }
        List<Merchant> list = merchantMapper.merchantSearch();
        List<JSONObject> joList = Lists.newArrayList();
        list.forEach(m ->{
            JSONObject jo = new JSONObject();
            jo.put("id", m.getId());
            jo.put("shop", m.getShop());
            jo.put("mobile", m.getMobile());
            joList.add(jo);
        });
        return new BaseResult<>(joList);
    }

    @Override
    public BaseResult<?> merchantInfo() {
        MerchantVO mvo = UserUtil.getMerchant();
        if (mvo == null) {
            return new BaseResult<>(BaseEnum.No_Login);
        }
        mvo.setOpeUserWx(null);
        Order order = orderMapper.getOrder1(mvo.getId());
        if (order != null) {
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
        asyncService.asyncAudit(mvo, "店铺认证审核,店铺ID:" + shopId);
        return new BaseResult<>();
    }

    @Override
    public BaseResult<?> merchantShop() {
        MerchantVO mvo = UserUtil.getMerchant();
        if (mvo == null) {
            return new BaseResult<>(BaseEnum.No_Login);
        }

        JSONObject jo = new JSONObject();
        Merchant merchant = merchantMapper.getMerchant2(mvo.getId());
        // // 状态：待审核-0、审核通过-1、已拒绝-2
        if (merchant != null) {
            jo.put("shop", merchant.getShop());
            jo.put("goods", merchant.getGoods());
            jo.put("shopId", merchant.getShopId());
            jo.put("introduce", merchant.getIntroduce());
            jo.put("auditRemark", merchant.getAuditRemark());
            if (StringUtils.isEmpty(merchant.getShop())) {
                jo.put("auditStatus", "未绑定");
            } else {
                if (StringUtils.isBlank(merchant.getAuditStatus())) {
                    jo.put("auditStatus", "未绑定");
                } else {
                    jo.put("auditStatus", Constant.auditStatusMap.get(GeneralUtil.parseInt(merchant.getAuditStatus())));
                }
            }
        }
        return new BaseResult<>(jo);
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
        if (ex != null && ex == mvo.getId()) {
            return new BaseResult<>(14, "店铺已被认证");
        } else {
            // 店铺修改
            merchantMapper.modifyShop(mvo.getId(), shopId, shop, goods, introduce);
            // async
            asyncService.asyncAudit(mvo, "店铺修改审核,店铺ID:" + shopId);
            return new BaseResult<>();
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
        merchantMapper.modifyShop2(mvo.getId(), null, null, null, null, null);
        return new BaseResult<>();
    }

    @Override
    public BaseResult<?> videoCentre(Integer type, Integer page) {
        MerchantVO vo = UserUtil.getMerchant();
        if (vo == null) {
            return new BaseResult<>(BaseEnum.No_Login);
        }
        List<Integer> list = Lists.newArrayList();
        if (type == 0) {
            // 全部：0、1、2、3
            list.add(0);
        } else if (vo.getVipType() == 3) {
            list.add(1);
            list.add(2);
            list.add(3);
        } else if (vo.getVipType() == 2) {
            list.add(1);
            list.add(2);
        } else if (vo.getVipType() == 1) {
            list.add(1);
        } else {
            if (type > vo.getVipType()) {
                return new BaseResult<>(16, "没有权限");
            }
            list.add(type);
        }
        int count = videoMapper.count(list);
        if (count == 0) {
            return new BaseResult<>();
        }
        List<Video> data = videoMapper.videoList(list, GeneralUtil.index2Page(page, 6), 6);
        data.forEach(v -> v.setCover(Constant.video_img));
        return new BaseResult<>(count, data);
    }

    @Override
    public BaseResult<?> videoPlay(Integer id) {
        MerchantVO mvo = UserUtil.getMerchant();
        if (mvo == null) {
            return new BaseResult<>(BaseEnum.No_Login);
        }

        Video video = videoMapper.getVideo(id);
        if (video == null) {
            return new BaseResult<>(12, "没有数据");
        }
        if (video.getLevel() > mvo.getVipType()) {
            return new BaseResult<>(13, "没有权限");
        }
        video.setCover(Constant.video_img);
        return new BaseResult<>(GeneralUtil.urlSignHandler(video.getPath()));
    }

    @Override
    public BaseResult<?> merchantContractContent() {
        MerchantVO mvo = UserUtil.getMerchant();
        if (mvo == null) {
            return new BaseResult<>(BaseEnum.No_Login);
        }
        MerchantSign merchantSign = merchantSignMapper.getOne(mvo.getId());
        if (merchantSign == null) {
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

        if (id != null) {
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
    public BaseResult<?> merchantSignCreate(Integer type, Integer buyType) {
        MerchantVO mvo = UserUtil.getMerchant();
        // type 0-企业、1-个人
        if (type == null) {
            return new BaseResult<>(13, "参数无效");
        }
        if (type != 1 && type != 0) {
            return new BaseResult<>(14, "参数错误");
        }
        boolean contains = !Constant.buyTypeMap.keySet().contains(buyType);
        if (buyType==null||contains){
            return new BaseResult<>(14, "参数错误");
        }

        String configStr = dataConfigMapper.getConfigStr(Constant.admin_id);
        String[] split = configStr.split(Constant.split2);
        String[] s = split[2].split(Constant.split);
        String fee;
        if (buyType == 1) {
            fee = s[0];
        } else if (buyType == 2) {
            fee = s[1];
        } else {
            fee = s[2];
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
        contract.setDocumentName(jo2.getString("filename"));
        contract.setFlowId(jo2.getString("flowId"));
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

    @Override
    public BaseResult<?> merchantSignStatus(String flowId) {
        MerchantVO mvo = UserUtil.getMerchant();
        if (mvo == null) {
            return new BaseResult<>(BaseEnum.No_Login);
        }
        boolean sign = cloudSignUtil.signStatus(flowId);
        if (sign) {
           contractMapper.updateStatus2(flowId);
        }
        return new BaseResult<>(sign);
    }

}
