package com.example.live.service;

import com.example.live.common.BaseResult;
import com.example.live.entity.LevelRight;

import java.util.List;

/**
 * @author baishuailei@zhejianglab.com
 * @date 2022/7/11 11:53
 */
public interface CommonService {

    List<LevelRight> getLevelRight(int level);

    // 返回当前登录业务员上层管理员用户
    Integer agentUser(Integer opeUser);

    BaseResult<?> msgList(Integer page);

    BaseResult<?> notificationMsg(String source);

    BaseResult<?> payConfigInfo();

    BaseResult<?> kef();

}
