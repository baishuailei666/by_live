package com.example.live.contorller;

import com.example.live.common.BaseResult;
import com.example.live.service.ResourceMerchantService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Base64;

/**
 * 商户资源
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
     * @param intention 意向程度：未联系-0、跟进中-1、已处理-2、已拒绝-3
     * @param page 1
     * @return
     */
    @GetMapping("/list")
    public BaseResult<?> resourceList(@Param("intention") Integer intention, @Param("page") Integer page) {
        return resourceMerchantService.resourceList(intention, page);
    }

}

