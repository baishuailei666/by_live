package com.example.live.util;

import com.example.live.common.Constant;
import com.example.live.vo.MerchantVO;
import com.example.live.vo.UserVO;
import org.springframework.beans.BeanUtils;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.Objects;

public class UserUtil {

    /**
     * 获取当前用户信息
     *
     * @return User
     */
    public static UserVO getUser() {
        Object obj = Objects.requireNonNull(RequestContextHolder.getRequestAttributes())
                .getAttribute(Constant.session_user, 1);
        if (obj == null) {
            // todo
            UserVO user1 = new UserVO();
            user1.setId(11);
            user1.setLevel(1);
            return user1;
        }
        UserVO user1 = new UserVO();
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
            MerchantVO mvo = new MerchantVO();
            mvo.setId(1);
            mvo.setOpeUser(11);
            return mvo;
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
