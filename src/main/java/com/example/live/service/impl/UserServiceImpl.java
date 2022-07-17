package com.example.live.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.example.live.common.BaseResult;
import com.example.live.common.Constant;
import com.example.live.controller.query.OrderQuery;
import com.example.live.entity.*;
import com.example.live.mapper.*;
import com.example.live.service.CommonService;
import com.example.live.service.UserService;
import com.example.live.util.*;
import com.example.live.vo.MerchantVO;
import com.example.live.vo.OrderVO;
import com.example.live.vo.UserVO;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Encoder;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author baishuailei@zhejianglab.com
 * @date 2022/7/6
 */
@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private MerchantMapper merchantMapper;
    @Autowired
    private MobileCodeMapper mobileCodeMapper;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private RelationUserMapper relationUserMapper;
    @Autowired
    private CommonService commonService;
    @Autowired
    private CloudSmsUtil cloudSmsUtil;



    @Override
    public BaseResult<?> userLogin(HttpSession session, JSONObject jo) {
        // opeUser 业务员
        Integer opeUser = jo.getInteger("opeUser");
        // source:back-管理端、merchant-商户端
        String source = jo.getString("source");
        String mobile = jo.getString("mobile");
        String code = jo.getString("code");
        String pwd = jo.getString("pwd");
        if (StringUtils.isBlank(mobile)) {
            return new BaseResult<>(10, "手机号不能未空");
        }
        if ("back".equals(source)) {
            // 后台管理系统
            String en = MD5Util.encode(pwd);
            User user = userMapper.getUser1(mobile, en);
            if (user==null) {
                return new BaseResult<>(12, "账号密码错误,登录失败");
            }
            UserVO vo = new UserVO();
            vo.setId(user.getId());
            vo.setLevel(user.getLevel());
            vo.setMobile(user.getMobile());
            if (user.getLevel()==3) {
                // 上级id
                Integer mid = relationUserMapper.getMainId(user.getId());
                vo.setAgentUser(mid);
            } else {
                // 超级管理员、管理员使用自己的id
                vo.setAgentUser(user.getId());
            }
            vo.setTs(user.getUt());
            List<LevelRight> rightList = commonService.getLevelRight(user.getLevel());
            vo.setRights(rightList);
            session.setAttribute(Constant.session_user, vo);
            return new BaseResult<>(vo);
        }

        if ("merchant".equals(source)) {
            MerchantVO mvo = new MerchantVO();
            if (StringUtils.isNotBlank(code)) {
                // 验证码登录
                String val = mobileCodeMapper.getCode(mobile);
                if (!Objects.equals(code, val)) {
                    return new BaseResult<>(11, "验证码不正确");
                }

                Merchant merchant = merchantMapper.getMerchant1(mobile);
                if (merchant==null) {
                    // 注册商户
                    merchantMapper.creatMerchant(mobile, GeneralUtil.defaultPwd(), opeUser);
                    String ts = DateUtil.getTime();
                    mvo.setOpeUser(opeUser);
                    mvo.setMobile(mobile);
                    mvo.setCt(ts);
                    mvo.setLt(ts);
                    session.setAttribute(Constant.session_user, mvo);
                    return new BaseResult<>(mvo);
                }
            }
            Merchant merchant = merchantMapper.getMerchant1(mobile);
            String en = MD5Util.encode(pwd);
            if (!Objects.equals(en, merchant.getPwd())) {
                return new BaseResult<>(12, "账号密码错误,登录失败");
            }
            // 登录商户
            mvo.setMobile(mobile);
            mvo.setCt(merchant.getCt());
            mvo.setLt(merchant.getLt());
            mvo.setShop(merchant.getShop());
            mvo.setShopId(merchant.getShopId());
            mvo.setOpeUser(merchant.getOpeUser());
            if (merchant.getOpeUser()!=null) {
                User user2 = userMapper.getUser2(merchant.getOpeUser());
                if (user2!=null) {
                    mvo.setOpeUserWx(user2.getWx());
                }
            }

            Order order = orderMapper.getOrder1(merchant.getId());
            if (order!=null) {
                mvo.setDays(GeneralUtil.buyDays(order.getBuyType(), order.getUt()));
                mvo.setBuyType(Constant.buyTypeMap.get(order.getBuyType()));
                mvo.setVipType(order.getBuyType());
            }
            List<LevelRight> rightList = commonService.getLevelRight(0);
            mvo.setRights(rightList);
            // 更新登录时间、登录次数
            merchantMapper.updateLt(merchant.getId(), merchant.getLoginCount()+1);
            session.setAttribute(Constant.session_user, mvo);
            return new BaseResult<>(mvo);
        }
        return new BaseResult<>();
    }

    @Override
    public BaseResult<?> userList(String keyword, Integer page) {
        Integer agentUser = UserUtil.getUserId();
        // 超级管理员查看所有
        if (agentUser==Constant.admin_id) {
            agentUser = null;
        }
        int count = userMapper.count(agentUser, keyword);
        if (count==0) {
            return new BaseResult<>();
        }
        List<User> list = userMapper.userList(agentUser, keyword, GeneralUtil.indexPage(page));
        return new BaseResult<>(count, list);
    }

    @Override
    public BaseResult<?> mobileCode(String mobile) {
        MobileCode mobileCode = mobileCodeMapper.getCodeEnt(mobile);
        if (mobileCode!=null) {
            boolean com = DateUtil.comTsVal(mobileCode.getTs(), 5);
            if (!com) {
                return new BaseResult<>(10, "请勿重复发送验证码,验证码有效期为5分钟");
            }
        }
        String context = GeneralUtil.get4Random();
        List<String> str = Lists.newArrayList();
        str.add(mobile);
        cloudSmsUtil.sendSms(str, context);
        mobileCodeMapper.insCode(mobile, context);
        return new BaseResult<>();
    }

    @Override
    public BaseResult<?> modifyPwd(JSONObject jo) {
        // source：back-管理端、merchant-商户端
        String source = jo.getString("source");
        String mobile = jo.getString("mobile");
        String code = jo.getString("code");
        String pwd = jo.getString("pwd");

        String val = mobileCodeMapper.getCode(mobile);
        if (StringUtils.isBlank(val)) {
            return new BaseResult<>(13, "验证码无效");
        }
        if (!val.equals(code)) {
            return new BaseResult<>(14, "验证码不正确");
        }
        String encode = MD5Util.encode(pwd);
        if ("back".equals(source)) {
            userMapper.modifyPwd(mobile, encode);
        } else if ("merchant".equals(source)) {
            merchantMapper.modifyPwd(mobile, encode);
        }
        return new BaseResult<>();
    }

    @Override
    public BaseResult<?> resetPwd(String mobile, String source) {
        // source：back-管理端、merchant-商户端
        if (Constant.source_back.equals(source)) {
            userMapper.modifyPwd(mobile, GeneralUtil.defaultPwd());
        } else if (Constant.source_merchant.equals(source)) {
            merchantMapper.modifyPwd(mobile, GeneralUtil.defaultPwd());
        }
        return new BaseResult<>();
    }

    @Override
    public BaseResult<?> uploadImg(MultipartFile file, Integer id) {
        BASE64Encoder b64 = new BASE64Encoder();
        try {
            String img = "data:" + file.getContentType()+";base64," + b64.encode(file.getBytes());
            userMapper.updateImg(img, id);
        } catch (IOException e) {
            e.printStackTrace();
            return new BaseResult<>(10, "上传失败");
        }
        return new BaseResult<>();
    }

    @Override
    public BaseResult<?> userCreate(JSONObject jo) {
        String mobile = jo.getString("mobile");
        String remark = jo.getString("remark");
        int level = jo.getIntValue("level");

        // 超级管理员-1、管理员（代理）-2、业务员-3
        if (level>3 || level<1) {
            return new BaseResult<>(10, "参数错误");
        }
        User user = userMapper.getUserMobile(mobile);
        if (user!=null) {
            return new BaseResult<>(11, "手机号已存在");
        }

        userMapper.insUser(mobile, GeneralUtil.defaultPwd(), level, remark);

        Integer loginUserId = UserUtil.getUserId();

        int userId = userMapper.lastId();
        // 用户从属关系
        relationUserMapper.insRelation(loginUserId, userId);
        return new BaseResult<>();
    }

    @Override
    public BaseResult<?> userDel(Integer id) {
        UserVO user = UserUtil.getUser();
        if (user == null) {
            return new BaseResult<>(10, "登录已过期，请重新登录");
        }
        if (user.getId() == id || id == Constant.admin_id) {
            return new BaseResult<>(14, "请求无效,请联系管理员");
        }
        userMapper.delUser(id);
        return new BaseResult<>();
    }

    @Override
    public BaseResult<?> orderList(OrderQuery query) {
        int count = orderMapper.orderCount(query);
        if (count==0) {
            return new BaseResult<>();
        }
        query.setPage(GeneralUtil.indexPage(query.getPage()));

        List<Order> data = orderMapper.orderList(query);
        List<Integer> ids = Lists.newArrayList();
        data.forEach(o -> ids.add(o.getMerchantId()));
        List<Merchant> merchantList = merchantMapper.merchantList(ids);
        Map<Integer, Merchant> merchantMap = merchantList.stream().collect(Collectors.toMap(Merchant::getId, Function.identity(), (k1, k2) -> k2));

        List<OrderVO> voList = Lists.newLinkedList();
        data.forEach(o ->{
            OrderVO ovo = new OrderVO();
            BeanUtils.copyProperties(o, ovo);
            Merchant merchant = merchantMap.get(o.getMerchantId());
            if (merchant!=null) {
                ovo.setMobile(merchant.getMobile());
                ovo.setShop(merchant.getShop());
            }
            ovo.setPayStatus(Constant.pay_success.equals(o.getStatus())?"已支付":"未支付");
            ovo.setBuyType1(Constant.buyTypeMap.get(o.getBuyType()));
            ovo.setPayType1(Constant.payTypeMap.get(o.getPayType()));
            voList.add(ovo);
        });
        return new BaseResult<>(count, voList);
    }

    @Override
    public BaseResult<?> orderIns(JSONObject jo) {
        UserVO uvo = UserUtil.getUser();
        int merchantId = jo.getIntValue("merchantId");
        int buyType = jo.getIntValue("buyType");
        double money = jo.getDoubleValue("money");
        int payType = jo.getIntValue("payType");

        Order order = new Order();
        order.setMerchantId(merchantId);
        order.setMoney(money);
        order.setPayType(payType);
        order.setBuyType(buyType);
        String orderNo = GeneralUtil.getOrderNo(buyType);
        order.setOrderNo(orderNo);
        order.setOpeUser(uvo.getId());
        order.setStatus(Constant.pay_success);
        orderMapper.insOrder(order);
        return new BaseResult<>();
    }

    @Override
    public BaseResult<?> opeUserMerchantOrderList(OrderQuery orderQuery) {
        // orderQuery start-查询时间开始、end-查询时间结束、page-1、mobile-商户手机号
        //  、shop-商户店铺、orderNo-订单号、buyType-购买类型 1-月卡、2-季卡、3-年卡
        UserVO user = UserUtil.getUser();
        orderQuery.setOpeUser(user.getId());
        return this.orderList(orderQuery);
    }

}
