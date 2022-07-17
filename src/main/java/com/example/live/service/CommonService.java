package com.example.live.service;

import com.alibaba.fastjson.JSONObject;
import com.example.live.common.BaseResult;
import com.example.live.entity.LevelRight;
import com.example.live.entity.PayConfig;

import java.util.List;

/**
 * @author baishuailei@zhejianglab.com
 * @date 2022/7/11 11:53
 */
public interface CommonService {

    List<LevelRight> getLevelRight(int level);

    // 返回当前登录业务员上层管理员用户
    Integer agentUser(Integer opeUser);

    Integer merchantAgentUser(Integer opeUser);

    // 返回当前管理员下层业务员
    List<Integer> opeUserIds(Integer agentUser);

    BaseResult<?> msgList(Integer page);

    BaseResult<?> notificationMsg(String source);

    BaseResult<?> payConfigInfo();

    BaseResult<?> kef();

    BaseResult<?> dataConfig(String mobile);

    BaseResult<?> dataConfigModify(JSONObject jo);

    BaseResult<?> payConfig(String mobile);

    BaseResult<?> payConfigIns(PayConfig payConfig);

    BaseResult<?> payConfigDel(Integer id);

    BaseResult<?> configModifyPrices(JSONObject jsonObject);

    BaseResult<?> showPrices();
}
