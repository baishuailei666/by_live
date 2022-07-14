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
import com.example.live.vo.UserVO;
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
    public BaseResult<?> resourceList(String mobile, String shop, Integer intention, Integer page) {
        UserVO userVO = UserUtil.getUser();

        Integer agentUser;
        Integer loginUser = userVO.getId();
        if (userVO.getLevel()==3) {
            agentUser = commonService.agentUser(loginUser);
        } else {
            agentUser = loginUser;
            loginUser = null;
        }

        int count = resourceMerchantMapper.resourceCount(agentUser, loginUser, intention, mobile, shop);
        if (count==0) {
            return new BaseResult<>();
        }
        List<ResourceMerchant> data = resourceMerchantMapper.resourceList(agentUser, loginUser, intention, mobile, shop, GeneralUtil.indexPage(page));
        List<ResourceMerchantVO> voList = Lists.newLinkedList();
        List<Integer> ids = Lists.newArrayList();
        data.forEach(rm -> ids.add(rm.getId()));

        // 业务员
        if (userVO.getLevel()==3) {
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
        } else {
            data.forEach(rm ->{
                ResourceMerchantVO vo = new ResourceMerchantVO();
                BeanUtils.copyProperties(rm, vo);
                voList.add(vo);
            });
        }
        return new BaseResult<>(count, voList);
    }

    @Override
    public BaseResult<?> editResource(JSONObject jo) {
        Integer intention = jo.getInteger("intention");
        Integer id = jo.getInteger("id");
        String note = jo.getString("note");
        //更新资源池
        resourceMerchantMapper.updateResourceMerchant(intention, id);
        //更新备注表  意向程度：跟进中-1、已拒绝-2、已处理-3
        contentMapper.insertByOid(id, note, intention);
        return new BaseResult<>();
    }

    @Override
    public BaseResult<?> nodes(Integer rid,Integer page) {
        return new BaseResult<>(contentMapper.nodeList(rid, GeneralUtil.indexPage(page))) ;
    }

}
