package com.example.live.service;

import com.alibaba.fastjson.JSONObject;
import com.example.live.common.BaseResult;

/**
 * @author baishuailei@zhejianglab.com
 * @date 2022/7/10 13:43
 */
public interface ResourceMerchantService {

    BaseResult<?> resourceList(String mobile, String shop, Integer intention, Integer page);

    BaseResult<?> editResource(JSONObject jo);

    BaseResult<?> nodes(Integer rid,Integer page);


}
