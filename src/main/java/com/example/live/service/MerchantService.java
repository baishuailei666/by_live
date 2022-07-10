package com.example.live.service;

import com.alibaba.fastjson.JSONObject;
import com.example.live.common.BaseResult;

/**
 * @author baishuailei@zhejianglab.com
 * @date 2022/7/10 13:42
 */
public interface MerchantService {

    /**
     * 查询商户列表，包含手机、店铺名、状态条件筛选
     * @param jo
     * @return
     */
    BaseResult getMerchantListByParams(JSONObject jo);

}
