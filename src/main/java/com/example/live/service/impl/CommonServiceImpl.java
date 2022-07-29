package com.example.live.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.example.live.common.BaseEnum;
import com.example.live.common.BaseResult;
import com.example.live.common.Constant;
import com.example.live.entity.*;
import com.example.live.mapper.*;
import com.example.live.service.CommonService;
import com.example.live.util.CloudCosUtil;
import com.example.live.util.GeneralUtil;
import com.example.live.util.UserUtil;
import com.example.live.vo.DataConfigVO;
import com.example.live.vo.MerchantVO;
import com.example.live.vo.UserVO;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
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
    private UserMapper userMapper;
    @Autowired
    private CommonService commonService;
    @Autowired
    private CloudCosUtil cloudCosUtil;
    @Autowired
    private VideoMapper videoMapper;


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
    public BaseResult<?> kef() {
        MerchantVO mvo = UserUtil.getMerchant();
        if (mvo == null) {
            return new BaseResult<>();
        }
        User user2 = userMapper.getUser2(mvo.getOpeUser());
        String val = dataConfigMapper.getConfigStr(Constant.admin_id);
        String[] phone = GeneralUtil.getAgentConfig(val, 1);
        JSONObject jo = new JSONObject();
        // 业务员微信
        jo.put("opeUserWx", user2.getWx());
        // 客服电话
        jo.put("kef1", phone[0]);
        jo.put("kef2", phone[1]);
        // 客服时间
        jo.put("kefTime", "09:00-18:00");
        return new BaseResult<>(jo);
    }

    @Override
    public BaseResult<?> dataConfig() {
        DataConfig c = dataConfigMapper.getContent2(Constant.admin_id);
        List<DataConfigVO> voList = Lists.newLinkedList();
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
        voList.add(vo);
        return new BaseResult<>(voList.size(), voList);
    }

    @Override
    public BaseResult<?> dataConfigModify(JSONObject jo) {
        UserVO user = UserUtil.getUser();
        if (user == null) {
            return new BaseResult<>(BaseEnum.No_Login);
        }
        if (user.getId() != Constant.admin_id) {
            return new BaseResult<>(13, "您没有权限操作！");
        }
        String emailSend = jo.getString("emailSend");
        String emailReceive = jo.getString("emailReceive");
        String kef1 = jo.getString("kef1");
        String kef2 = jo.getString("kef2");
        // 发件邮箱,收件邮箱;客服电话1,客服电话2;月卡,季卡,年卡
        String content = emailSend + Constant.split + emailReceive + Constant.split2 + kef1;
        if (StringUtils.isNotEmpty(kef2)) {
            content += Constant.split + kef2;
        }
        dataConfigMapper.modifyConfig(Constant.admin_id, content);
        return new BaseResult<>();
    }

    @Override
    public BaseResult<?> configModifyPrices(JSONObject jo) {
        UserVO user = UserUtil.getUser();
        if (user == null) {
            return new BaseResult<>(BaseEnum.No_Login);
        }
        if (user.getId() != Constant.admin_id) {
            return new BaseResult<>(13, "您没有权限操作！");
        }
        double month = jo.getDoubleValue("month");
        double quarter = jo.getDoubleValue("quarter");
        double year = jo.getDoubleValue("year");
        DataConfig data = dataConfigMapper.getContent2(Constant.admin_id);
        String[] split = data.getContent().split(Constant.split2);
        String sbd = split[0] +
                Constant.split2 +
                split[1] +
                Constant.split2 +
                month + Constant.split + quarter + Constant.split + year;
        dataConfigMapper.modifyConfig(Constant.admin_id, sbd);
        return new BaseResult<>();
    }

    @Override
    public BaseResult<?> showPrices() {
        MerchantVO merchant = UserUtil.getMerchant();
        if (merchant == null) {
            return new BaseResult<>(BaseEnum.No_Login);
        }
        //展示价格，只有超管账号下才有月季年卡价格信息
        String configStr = dataConfigMapper.getConfigStr(Constant.admin_id);
        String[] split = configStr.split(Constant.split2);
        String[] s = split[2].split(Constant.split);
        DataConfigVO dataConfigVO = new DataConfigVO();
        dataConfigVO.setMonthCard(s[0]);
        dataConfigVO.setSeasonCard(s[1]);
        dataConfigVO.setYearCard(s[2]);
        return new BaseResult<>(dataConfigVO);
    }

    @Override
    public BaseResult<?> uploadVideo(MultipartFile file, String title, Integer level) {
        if (StringUtils.isBlank(title) || level == null) {
            return new BaseResult<>(11, "参数不能为空");
        }
        String url = cloudCosUtil.uploadVideo(file);
        if (StringUtils.isNotBlank(url)) {
            videoMapper.insVideo(title, level, "/"+url);
        }
        return new BaseResult<>();
    }

}
