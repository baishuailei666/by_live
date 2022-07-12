package com.example.live.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.example.live.common.BaseResult;
import com.example.live.common.Constant;
import com.example.live.entity.Content;
import com.example.live.entity.LevelRight;
import com.example.live.mapper.ConfigurationMapper;
import com.example.live.mapper.ContentMapper;
import com.example.live.mapper.LevelRightMapper;
import com.example.live.mapper.RelationUserMapper;
import com.example.live.service.CommonService;
import com.example.live.util.GeneralUtil;
import com.example.live.util.UserUtil;
import com.example.live.vo.MerchantVO;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author baishuailei@zhejianglab.com
 * @date 2022/7/11 11:53
 */
@Service("commonService")
public class CommonServiceImpl implements CommonService {

    @Autowired
    private LevelRightMapper levelRightMapper;
    @Autowired
    private RelationUserMapper relationUserMapper;
    @Autowired
    private ContentMapper contentMapper;
    @Autowired
    private ConfigurationMapper configurationMapper;


    private static List<LevelRight> rightList;

    private List<LevelRight> getRightList() {
        if (rightList != null) {
            return rightList;
        }
        rightList = levelRightMapper.levelRightList();
        return rightList;
    }

    @Override
    public List<LevelRight> getLevelRight(int level) {
        List<LevelRight> list = getRightList();
        return list.stream().filter(l -> l.getLevel()==level).collect(Collectors.toList());
    }

    @Override
    public Integer agentUser(Integer opeUser) {
        Integer mid = relationUserMapper.getMainId(opeUser);
        if (mid==null) {
            // 当前用户就是管理员
            mid = opeUser;
        }
        return mid;
    }

    @Override
    public BaseResult<?> msgList(Integer page) {
        List<Content> data = contentMapper.contentList(UserUtil.getMerchantId(), GeneralUtil.indexPage(page));
        return new BaseResult<>(data);
    }

    @Override
    public BaseResult<?> notificationMsg(String source) {
        Integer val = null;
        if (Constant.source_back.equals(source)) {
            // 后台接收消息
            val = contentMapper.getMsg3(UserUtil.getUserId());
        }
        if (Constant.source_merchant.equals(source)) {
            // 商户端接收消息
            val = contentMapper.getMsg3(UserUtil.getMerchantId());
        }
        return new BaseResult<>(val);
    }

    @Override
    public BaseResult<?> payConfigInfo() {
        // 逗号分隔：发件邮箱,收件邮箱;客服电话1,客服电话2;月卡,季卡,年卡
        String val = configurationMapper.getConfigStr(Constant.admin_id);
        String[] price = GeneralUtil.getAgentConfig(val, 3);
        List<JSONObject> joList = Lists.newLinkedList();
        JSONObject j1 = new JSONObject();
        j1.put("type", 1);
        j1.put("price", price[0]);
        joList.add(j1);
        JSONObject j2 = new JSONObject();
        j2.put("type", 2);
        j2.put("price", price[1]);
        joList.add(j2);
        JSONObject j3 = new JSONObject();
        j3.put("type", 3);
        j3.put("price", price[2]);
        joList.add(j3);
        return new BaseResult<>(joList);
    }

    @Override
    public BaseResult<?> kef() {
        MerchantVO mvo = UserUtil.getMerchant();
        if (mvo==null) {
            return new BaseResult<>();
        }
        Integer mid = relationUserMapper.getMainId(mvo.getOpeUser());
        String val = configurationMapper.getConfigStr(mid);
        String[] phone = GeneralUtil.getAgentConfig(val, 1);
        return new BaseResult<>(phone[0]+"\n"+phone[1]);
    }

}
