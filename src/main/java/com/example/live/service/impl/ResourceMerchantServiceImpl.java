package com.example.live.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.example.live.common.BaseResult;
import com.example.live.entity.Content;
import com.example.live.entity.ResourceMerchant;
import com.example.live.mapper.ContentMapper;
import com.example.live.mapper.ResourceMerchantMapper;
import com.example.live.service.CommonService;
import com.example.live.service.ResourceMerchantService;
import com.example.live.util.GeneralUtil;
import com.example.live.util.UserUtil;
import com.example.live.vo.ResourceMerchantVO;
import com.google.common.collect.Lists;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

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
        List<ResourceMerchantVO> voList = Lists.newLinkedList();
        List<Integer> ids = Lists.newArrayList();
        data.forEach(rm -> ids.add(rm.getId()));

        // 备注信息
        List<Content> conList = contentMapper.contentList2(loginUser, ids, 2);
        Map<Integer, List<Content>> conMap = conList.stream().collect(Collectors.groupingBy(Content::getRid));
        data.forEach(rm ->{
            List<Content> list = conMap.getOrDefault(rm.getId(), new ArrayList<>());
            list.sort(Comparator.comparing(Content::getTs));
            Collections.reverse(list);

            ResourceMerchantVO vo = new ResourceMerchantVO();
            BeanUtils.copyProperties(rm, vo);
            vo.setContentTotal(list.size());
            if (list.size()!=0) {
                vo.setContent(list.get(0).getNote());
            }
            voList.add(vo);
        });
        return new BaseResult<>(count, voList);
    }

    @Override
    public BaseResult<?> editResource(JSONObject jo) {
        Integer intention = jo.getInteger("intention");
        Integer id = jo.getInteger("id");
        String note = jo.getString("note");
        //更新资源池
        resourceMerchantMapper.updateResourceMerchant(intention, id);
        // todo
        //更新备注表  意向程度：跟进中-1、已处理-2、已拒绝-3
        contentMapper.insertByOid(id, note, intention);
        return new BaseResult<>();
    }

}
