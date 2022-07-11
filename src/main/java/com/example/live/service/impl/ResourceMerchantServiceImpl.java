package com.example.live.service.impl;

import com.example.live.common.BaseResult;
import com.example.live.entity.ResourceMerchant;
import com.example.live.mapper.ResourceMerchantMapper;
import com.example.live.service.CommonService;
import com.example.live.service.ResourceMerchantService;
import com.example.live.util.GeneralUtil;
import com.example.live.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author baishuailei@zhejianglab.com
 * @date 2022/7/10 13:43
 */
@Service("resourceMerchantService")
public class ResourceMerchantServiceImpl implements ResourceMerchantService {

    @Autowired
    private ResourceMerchantMapper resourceMerchantMapper;
    @Autowired
    private CommonService commonService;


    @Override
    public BaseResult<?> resourceList(Integer intention, Integer page) {
        Integer loginUser = UserUtil.getUserId();
        Integer agentUser = commonService.agentUser(loginUser);
        int count = resourceMerchantMapper.resourceCount(agentUser, loginUser, intention);
        if (count==0) {
            return new BaseResult<>();
        }
        List<ResourceMerchant> data = resourceMerchantMapper.resourceList(agentUser, loginUser, intention, GeneralUtil.indexPage(page));
        return new BaseResult<>(count, data);
    }
}
