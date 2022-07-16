package com.example.live.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.example.live.common.BaseResult;
import com.example.live.entity.User;
import com.example.live.mapper.MerchantAuditMapper;
import com.example.live.mapper.MerchantMapper;
import com.example.live.service.MerchantAuditService;
import com.example.live.util.GeneralUtil;
import com.example.live.util.UserUtil;
import com.example.live.vo.MerchantAuditVO;
import com.example.live.vo.UserVO;
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

    @Autowired
    private MerchantMapper merchantMapper;

    @Override
    public BaseResult<?> audits(JSONObject jo) {
        // 0-待审核、1-审核通过、2-审核拒绝
        Integer status = jo.getInteger("status");
        String mobile = jo.getString("mobile");
        String shop = jo.getString("shop");
        int page = jo.getInteger("page");
        UserVO user = UserUtil.getUser();
        if (user == null) {
            return new BaseResult<>(10, "登录已过期，重新登录！");
        }
        Integer userId = user.getId();
        int i = merchantAuditMapper.merchantAuditWaitCount(userId, status, mobile, shop);
        if (i!=0) {
            List<MerchantAuditVO> merchantAudits = merchantAuditMapper.merchantAuditWait(userId, status, mobile, shop, GeneralUtil.indexPage(page));
            merchantAudits.forEach(v ->{
                if ("1".equals(v.getStatus())) {
                    v.setStatus("已通过");
                } else if ("2".equals(v.getStatus())) {
                    v.setStatus("已拒绝");
                } else {
                    v.setStatus("待审核");
                }
            });
            return new BaseResult<>(i, merchantAudits);
        } else {
            return new BaseResult<>();
        }
    }

    @Override
    public BaseResult<?> merchantAudit(JSONObject jo) {
        String merchantId = jo.getString("merchantId");
        int status = jo.getIntValue("status");
        String reason = jo.getString("reason");
        if (StringUtils.isEmpty(merchantId)) {
            return new BaseResult<>(12, "参数不能为空");
        }
        if (status!=1&&status!=2) {
            return new BaseResult<>(14, "请求参数有误");
        }
        // 更新merchant_audit表
        merchantAuditMapper.updateMerchantAudit(merchantId, status, reason);
        if (status==1) {
            // 更新merchant表
            merchantMapper.updateMerchantCheck(merchantId);
        }
        return new BaseResult<>();
    }


}
