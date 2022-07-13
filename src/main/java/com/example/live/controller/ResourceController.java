package com.example.live.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.live.common.BaseResult;
import com.example.live.service.ResourceMerchantService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 商户资源
 *
 * @author baishuailei@zhejianglab.com
 * @date 2022/7/10 17:53
 */
@RestController
@RequestMapping("/resource")
public class ResourceController {

    @Autowired
    private ResourceMerchantService resourceMerchantService;

    /**
     * 商户资源列表
     *
     * @param mobile
     * @param shop
     * @param intention 意向程度：未联系-0、跟进中-1、已处理-2、已拒绝-3
     * @param page      1
     * @return
     */
    @GetMapping("/list")
    public BaseResult<?> resourceList(String mobile, String shop
            , Integer intention, Integer page) {
        return resourceMerchantService.resourceList(mobile, shop, intention, page);
    }


    /**
     * 商户资源意向程度修改
     *
     * @param jo id、intention：跟进中-1、已处理-2、已拒绝-3
     * @return
     */
    @PostMapping("/edit")
    public BaseResult<?> editResource(JSONObject jo) {
        return resourceMerchantService.editResource(jo);
    }


}

