package com.example.live.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.example.live.common.BaseResult;
import com.example.live.entity.User;
import com.example.live.mapper.MerchantAuditMapper;
import com.example.live.service.MerchantAuditService;
import com.example.live.util.GeneralUtil;
import com.example.live.util.UserUtil;
import com.example.live.vo.MerchantAuditVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author baishuailei@zhejianglab.com
 * @date 2022/7/10 13:45
 */
@Service("merchantAuditService")
public class MerchantAuditServiceImpl implements MerchantAuditService {


    @Autowired
    private MerchantAuditMapper merchantAuditMapper;

    @Override
    public BaseResult<?> audits(JSONObject jo) {
        String status = jo.getString("status");
        String mobile = jo.getString("mobile");
        String shop = jo.getString("shop");
        int page = jo.getInteger("page");
        int size = jo.getInteger("size");
        User user = UserUtil.getUser();
        if (user == null) {
            return new BaseResult<>(13, "登录已过期，重新登录！");
        }
        Integer userId = UserUtil.getUserId();
        int i = merchantAuditMapper.merchantAuditWaitCount(userId, status, mobile, shop);
        if (i != 0) {
            List<MerchantAuditVo> merchantAudits = merchantAuditMapper.merchantAuditWait(userId, status, mobile, shop, GeneralUtil.indexPage(page, size), size == 0 ? 10 : size);
            return new BaseResult<>(i, merchantAudits);
        } else {
            return new BaseResult<>();
        }

    }

    @Override
    public BaseResult<?> merchantAudit(JSONObject jo) {
        String shopId = jo.getString("shopId");
        Boolean status = jo.getBoolean("status");
        String reason = jo.getString("reason");
        if (StringUtils.isEmpty(shopId)) {
            return new BaseResult<>(14, "请求参数有误（shopId未传）");
        }
        if (status == null) {
            return new BaseResult<>(14, "请求参数有误（审核状态未传）");
        }
        //更新merchant_audit表
        merchantAuditMapper.updateMerchantAudit(shopId, status ? "审核通过" : "已拒绝", reason);
        //更新merchant表

        if (status) {
            //TODO 是否还得做其他操作
        }
        return new BaseResult<>();
    }


}
