package com.example.live.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.example.live.common.BaseResult;
import com.example.live.common.Constant;
import com.example.live.entity.*;
import com.example.live.mapper.*;
import com.example.live.service.CommonService;
import com.example.live.util.GeneralUtil;
import com.example.live.util.UserUtil;
import com.example.live.vo.DataConfigVO;
import com.example.live.vo.MerchantVO;
import com.example.live.vo.UserVO;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
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
    private DataConfigMapper dataConfigMapper;
    @Autowired
    private PayConfigMapper payConfigMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private CommonService commonService;


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
        return list.stream().filter(l -> l.getLevel() == level).collect(Collectors.toList());
    }

    @Override
    public Integer agentUser(Integer opeUser) {
        if (opeUser == Constant.admin_id) {
            return opeUser;
        }
        Integer mid = relationUserMapper.getMainId(opeUser);
        if (mid == null) {
            // 当前用户就是管理员
            mid = opeUser;
        }
        return mid;
    }

    @Override
    public Integer merchantAgentUser(Integer opeUser) {
        if (opeUser == null) {
            return Constant.admin_id;
        }
        Integer mid = relationUserMapper.getMainId(opeUser);
        if (mid == null) {
            // 当前用户就是管理员
            mid = opeUser;
        }
        return mid;
    }

    @Override
    public List<Integer> opeUserIds(Integer agentUser) {
        if (agentUser == Constant.admin_id) {
            return null;
        }
        return relationUserMapper.relationIds(agentUser);
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
        String val = dataConfigMapper.getConfigStr(Constant.admin_id);
        String[] price = GeneralUtil.getAgentConfig(val, 2);
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
        if (mvo == null) {
            return new BaseResult<>();
        }
        Integer mid = commonService.agentUser(mvo.getOpeUser());
        String val = dataConfigMapper.getConfigStr(mid);
        String[] phone = GeneralUtil.getAgentConfig(val, 1);
        return new BaseResult<>(phone[0] + ";" + phone[1]);
    }

    @Override
    public BaseResult<?> dataConfig(String mobile) {
        Integer agentUser = null;
        if (StringUtils.isNotBlank(mobile)) {
            User user = userMapper.getUserMobile(mobile);
            if (user != null) {
                agentUser = user.getId();
            }
        }
        List<DataConfig> data = dataConfigMapper.configList(agentUser);
        List<DataConfigVO> voList = Lists.newLinkedList();
        data.forEach(c -> {
            DataConfigVO vo = new DataConfigVO();
            vo.setCt(c.getCt());
            vo.setId(c.getId());
            String val = c.getContent();
            // 0-邮箱地址、1-客服电话、2-服务价格
            String[] email = GeneralUtil.getAgentConfig(val, 0);
            String[] kef = GeneralUtil.getAgentConfig(val, 1);
            String[] price = GeneralUtil.getAgentConfig(val, 2);
            // 发件邮箱,收件邮箱;客服电话1,客服电话2;月卡,季卡,年卡
            vo.setEmailSend(email[0]);
            vo.setEmailReceive(email[1]);
            vo.setKef1(kef[0]);
            vo.setKef2(kef[1]);
            vo.setMonthCard(price[0]);
            vo.setSeasonCard(price[1]);
            vo.setYearCard(price[2]);
            vo.setAgentUser(c.getAgentUser());
            vo.setAgentRemark(c.getAgentRemark());
            vo.setMobile(c.getMobile());
            voList.add(vo);
        });
        return new BaseResult<>(voList.size(), voList);
    }

    @Override
    public BaseResult<?> dataConfigModify(JSONObject jo) {
        Integer id = jo.getInteger("id");
        Integer agentUser = jo.getInteger("agentUser");
        String emailSend = jo.getString("emailSend");
        String emailReceive = jo.getString("emailReceive");
        String kef1 = jo.getString("kef1");
        String kef2 = jo.getString("kef2");
        String monthCard = jo.getString("monthCard");
        String seasonCard = jo.getString("seasonCard");
        String yearCard = jo.getString("yearCard");
        // 发件邮箱,收件邮箱;客服电话1,客服电话2;月卡,季卡,年卡
        String content = emailSend + "," + emailReceive + ";" + kef1 + "," + kef2 + ";" + monthCard + "," + seasonCard + "," + yearCard;
        if (id != null) {
            DataConfig dc = dataConfigMapper.getContent(id);
            if (dc == null) {
                return new BaseResult<>(10, "无效id,数据不存在");
            }
            if (dc.getAgentUser() != agentUser) {
                return new BaseResult<>(11, "参数错误,数据不匹配");
            }
            // 修改
            dataConfigMapper.modifyConfig(id, agentUser, content);
        } else {
            String val = dataConfigMapper.getConfigStr(agentUser);
            if (StringUtils.isNotBlank(val)) {
                return new BaseResult<>(12, "不能重复添加");
            }
            // 添加
            dataConfigMapper.insConfig(agentUser, content);
        }
        return new BaseResult<>();
    }

    @Override
    public BaseResult<?> payConfig(String mobile) {
        Integer agentUser = null;
        if (StringUtils.isNotBlank(mobile)) {
            User user = userMapper.getUserMobile(mobile);
            if (user != null) {
                agentUser = user.getId();
            }
        }
        List<PayConfig> data = payConfigMapper.configList(agentUser);
        return new BaseResult<>(data.size(), data);
    }

    @Override
    public BaseResult<?> payConfigIns(PayConfig payConfig) {
        payConfigMapper.insConfig(payConfig);
        return new BaseResult<>();
    }

    @Override
    public BaseResult<?> payConfigDel(Integer id) {
        payConfigMapper.delConfig(id);
        return new BaseResult<>();
    }

    @Override
    public BaseResult<?> configModifyPrices(Double month, Double quarter, Double year) {
        UserVO user = UserUtil.getUser();
        if (user == null) {
            return new BaseResult<>(14, "登录已过期，请重新登录！");
        }
        if (user.getLevel() != 1 && user.getId()!=Constant.admin_id) {
            return new BaseResult<>(13, "您没有权限操作！");
        }
        DataConfig data = dataConfigMapper.getContent2(user.getId());
        String[] split = data.getContent().split(Constant.split2);
        String sbd = split[0] +
                Constant.split2 +
                split[1] +
                Constant.split2 +
                month + Constant.split + quarter + Constant.split + year;
        dataConfigMapper.modifyConfig(data.getId(), data.getAgentUser(), sbd);
        return new BaseResult<>();
    }

    @Override
    public BaseResult<?> showPrices() {
        UserVO user = UserUtil.getUser();
        if (user == null) {
            return new BaseResult<>(14, "登录已过期，请重新登录！");
        }
        String configStr = dataConfigMapper.getConfigStr(user.getId());
        String[] split = configStr.split(";");
        String[] s = split[2].split(",");
        DataConfigVO dataConfigVO = new DataConfigVO();
        dataConfigVO.setMonthCard(s[0]);
        dataConfigVO.setSeasonCard(s[1]);
        dataConfigVO.setYearCard(s[2]);
        return new BaseResult<>(dataConfigVO);
    }

}
