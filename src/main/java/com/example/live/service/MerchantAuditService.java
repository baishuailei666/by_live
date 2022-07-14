package com.example.live.service;

import com.alibaba.fastjson.JSONObject;
import com.example.live.common.BaseResult;

/**
 * @author baishuailei@zhejianglab.com
 * @date 2022/7/10 13:45
 */
public interface MerchantAuditService {

    BaseResult<?> audits(JSONObject jo);

    BaseResult<?> merchantAudit(JSONObject jo);
}
