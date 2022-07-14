package com.example.live.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.live.common.BaseResult;
import com.example.live.service.ResourceMerchantService;
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
     * 商户资源-意向程度修改
     *
     * @param jo id、intention：跟进中-1、已处理-2、已拒绝-3
     * @return
     */
    @PostMapping("/edit")
    public BaseResult<?> editResource(JSONObject jo) {
        return resourceMerchantService.editResource(jo);
    }

    /**
     * 商户资源-添加备注
     *
     * @param jo id、note
     * @return
     */
    @PostMapping("/note/add")
    public BaseResult<?> noteAdd(@RequestBody JSONObject jo) {
        return resourceMerchantService.noteAdd(jo);
    }

    /**
     * 商户资源-备注列表
     *
     * @param id
     * @return
     */
    @GetMapping("/note")
    public BaseResult<?> noteList(@RequestParam("id") Integer id) {
        return resourceMerchantService.noteList(id);
    }


}

