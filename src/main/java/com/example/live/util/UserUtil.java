package com.example.live.util;

import com.example.live.common.Constant;
import com.example.live.vo.MerchantVO;
import com.example.live.vo.UserVO;
import org.springframework.beans.BeanUtils;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.Objects;

/**
 * 管理端登录返回user
 * 商户端登录返回merchant
 */
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
            return null;
        }
        UserVO user1 = new UserVO();
        BeanUtils.copyProperties(obj, user1);
        return user1;
    }

    public static Integer getUserId() {
        if (getUser() != null) {
            return getUser().getId();
        } else {
            return null;
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
            return null;
        }
    }


}
