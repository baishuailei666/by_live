package com.example.live.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.example.live.common.BaseResult;
import com.example.live.common.Constant;
import com.example.live.entity.Merchant;
import com.example.live.entity.Order;
import com.example.live.entity.User;
import com.example.live.mapper.MerchantMapper;
import com.example.live.mapper.MobileCodeMapper;
import com.example.live.mapper.OrderMapper;
import com.example.live.mapper.UserMapper;
import com.example.live.service.UserService;
import com.example.live.util.DateUtil;
import com.example.live.util.GeneralUtil;
import com.example.live.util.MD5Util;
import com.example.live.util.UserUtil;
import com.example.live.vo.MerchantVO;
import com.example.live.vo.UserVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Objects;

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
                    merchantMapper.creatMerchant(mobile, opeUser);
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
            mvo.setOpeUser(merchant.getOpeUser());

            Order order = orderMapper.getOrder1(merchant.getId());
            if (order!=null) {
                mvo.setDays(GeneralUtil.buyDays(order.getBuyType(), order.getUt()));
                mvo.setBuyType(Constant.buyTypeMap.get(order.getBuyType()));
            }
            merchantMapper.updateLt(merchant.getId());
            session.setAttribute(Constant.session_user, mvo);
            return new BaseResult<>(mvo);
        }
        return new BaseResult<>();
    }

    @Override
    public BaseResult<?> userList(String keyword, Integer page) {
        Integer agentUser = UserUtil.getUserId();

        List<User> list = userMapper.userList(agentUser, keyword, GeneralUtil.indexPage(page, 10));
        return new BaseResult<>(list);
    }
}
