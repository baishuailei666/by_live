package com.example.live.util;

import com.example.live.common.Constant;
import com.example.live.entity.Order;
import com.example.live.mapper.OrderMapper;
import com.example.live.vo.MerchantVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.Objects;

/**
 * 需要用户实时状态
 *
 * @author baishuailei@zhejianglab.com
 * @date 2022/8/21 21:49
 */
@Component
public class MerchantUtil {

    @Autowired
    private OrderMapper orderMapper;

    public MerchantVO getMerchant() {
        Object obj = Objects.requireNonNull(RequestContextHolder.getRequestAttributes())
                .getAttribute(Constant.session_user2, 1);
        if (obj == null) {
            return null;
        }
        MerchantVO mvo = new MerchantVO();
        BeanUtils.copyProperties(obj, mvo);

        // 获取最新订单信息
        Order order = orderMapper.getOrder1(mvo.getId());
        if (order != null) {
            mvo.setDays(GeneralUtil.buyDays(order.getBuyType(), order.getUt()));
            mvo.setBuyType(Constant.buyTypeMap.get(order.getBuyType()));
            mvo.setVipType(order.getBuyType());
        } else {
            mvo.setBuyType(null);
            mvo.setVipType(0);
            mvo.setDays(0);
        }
        return mvo;
    }

    public Integer getMerchantId() {
        if (getMerchant() != null) {
            return getMerchant().getId();
        } else {
            return null;
        }
    }
}
