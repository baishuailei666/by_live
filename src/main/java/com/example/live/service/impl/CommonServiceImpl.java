package com.example.live.service.impl;

import com.example.live.common.BaseResult;
import com.example.live.common.Constant;
import com.example.live.entity.Content;
import com.example.live.entity.LevelRight;
import com.example.live.mapper.ContentMapper;
import com.example.live.mapper.LevelRightMapper;
import com.example.live.mapper.RelationUserMapper;
import com.example.live.service.CommonService;
import com.example.live.util.GeneralUtil;
import com.example.live.util.UserUtil;
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

}
