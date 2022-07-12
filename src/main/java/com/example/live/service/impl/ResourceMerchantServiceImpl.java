package com.example.live.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.example.live.common.BaseResult;
import com.example.live.entity.ResourceMerchant;
import com.example.live.mapper.ContentMapper;
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
    @Autowired
    private ContentMapper contentMapper;


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

    @Override
    public BaseResult<?> editResource(JSONObject jo) {
        Integer intention = jo.getInteger("intention");
        Integer id = jo.getInteger("id");
        String node = jo.getString("node");
        //更新资源池
        resourceMerchantMapper.updateResourceMerchant(intention, id);
        //更新备注表  意向程度：未联系-0、跟进中-1、已处理-2、已拒绝-3
        contentMapper.insertByOid(id, node, intention);
        return new BaseResult<>();
    }

}
