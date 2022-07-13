package com.example.live.util;

import com.example.live.common.Constant;
import com.example.live.entity.User;
import com.example.live.vo.MerchantVO;
import org.springframework.beans.BeanUtils;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.Objects;

public class UserUtil {

    /**
     * 获取当前用户信息
     *
     * @return User
     */
    public static User getUser() {
        Object obj = Objects.requireNonNull(RequestContextHolder.getRequestAttributes())
                .getAttribute(Constant.session_user, 1);
        if (obj == null) {
            return null;
        }
        User user1 = new User();
        BeanUtils.copyProperties(obj, user1);
        return user1;
    }

    public static Integer getUserId() {
        if (getUser() != null) {
            return getUser().getId();
        } else {
            return 11;
        }
    }

    // 商户信息
    public static MerchantVO getMerchant() {
        Object obj = Objects.requireNonNull(RequestContextHolder.getRequestAttributes())
                .getAttribute(Constant.session_user, 1);
        if (obj == null) {
            return null;
        }
        MerchantVO mvo = new MerchantVO();
        BeanUtils.copyProperties(obj, mvo);
        return mvo;
    }

    public static Integer getMerchantId() {
        if (getMerchant() != null) {
            return getMerchant().getId();
        } else {
            return 1;
        }
    }


}
