package com.example.live.service;

import com.example.live.common.BaseResult;

/**
 * @author baishuailei@zhejianglab.com
 * @date 2022/7/10 13:43
 */
public interface ResourceMerchantService {

    BaseResult<?> resourceList(Integer intention, Integer page);
}
