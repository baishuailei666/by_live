package com.example.live.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.example.live.common.BaseEnum;
import com.example.live.common.BaseResult;
import com.example.live.common.Constant;
import com.example.live.entity.Anchor;
import com.example.live.mapper.AnchorMapper;
import com.example.live.mapper.ContentMapper;
import com.example.live.service.AnchorService;
import com.example.live.util.GeneralUtil;
import com.example.live.util.UserUtil;
import com.example.live.vo.AnchorVO;
import com.example.live.vo.ContentVO;
import com.example.live.vo.MerchantVO;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author baishuailei@zhejianglab.com
 * @date 2022/7/10 13:49
 */
@Service("anchorService")
public class AnchorServiceImpl implements AnchorService {

    @Autowired
    private AnchorMapper anchorMapper;
    @Autowired
    private ContentMapper contentMapper;

    private List<JSONObject> categoryHandler(String category) {
        if (StringUtils.isBlank(category)) {
            return null;
        }
        List<JSONObject> joList = Lists.newArrayList();
        if (category.contains(Constant.split3)) {
            String[] s1 = category.split(Constant.split3);
            for (String s:s1) {
                if (s.contains("=")) {
                    String[] s2 = s.split("=");

                    JSONObject jo = new JSONObject();
                    jo.put("key", s2[0]);
                    jo.put("val", s2[1]);
                    joList.add(jo);
                }
            }
        } else if (category.contains(Constant.split2)) {
            String[] s1 = category.split(Constant.split2);
            for (String s:s1) {
                if (s.contains("=")) {
                    String[] s2 = s.split("=");

                    JSONObject jo = new JSONObject();
                    jo.put("key", s2[0]);
                    jo.put("val", s2[1]);
                    joList.add(jo);
                }
            }
        } else if (category.contains("；")) {
            String[] s1 = category.split("；");
            for (String s:s1) {
                if (s.contains("=")) {
                    String[] s2 = s.split("=");

                    JSONObject jo = new JSONObject();
                    jo.put("key", s2[0]);
                    jo.put("val", s2[1]);
                    joList.add(jo);
                }
            }
        } else {
            if (category.contains("=")) {
                String[] s2 = category.split("=");
                for (String s:s2) {
                    String[] s3 = s.split("=");

                    JSONObject jo = new JSONObject();
                    jo.put("key", s3[0]);
                    jo.put("val", s3[1]);
                    joList.add(jo);
                }
            }
        }
        return joList;
    }
    @Override
    public BaseResult<?> anchorInfo(Integer id) {
        MerchantVO mvo = UserUtil.getMerchant();
        if (mvo==null) {
            return new BaseResult<>(BaseEnum.No_Login);
        }
        if (mvo.getVipType()==0) {
            return new BaseResult<>(12, "没有权限");
        }
        Anchor anchor = anchorMapper.anchorInfo(id);
        if (anchor!=null) {
            String category = anchor.getCategory();
            anchor.setCateList(categoryHandler(category));
        }
        return new BaseResult<>(anchor);
    }

    @Override
    public BaseResult<?> anchorList(String category,Integer page) {
        int i = anchorMapper.anchorListCount(category);
        if (i == 0) {
            return new BaseResult<>();
        }
        List<Anchor> data = anchorMapper.anchorList(category, GeneralUtil.indexPage(page));
        List<AnchorVO> voList = Lists.newLinkedList();
        data.forEach(a -> {
            AnchorVO avo = new AnchorVO();
            BeanUtils.copyProperties(a, avo);
            String val = GeneralUtil.getStarString(a.getNickname(), 1, 1);
            avo.setNickname(val);
            List<JSONObject> list = categoryHandler(a.getCategory());
            if (list!=null) {
                List<String> list1 = Lists.newLinkedList();
                list1.add(list.get(0).getString("key"));
                if (list.size()>2) {
                    list1.add(list.get(1).getString("key"));
                }
                avo.setCategory(list1);
            }
            voList.add(avo);
        });
        return new BaseResult<>(data.size(), voList);
    }

    @Override
    public BaseResult<?> anchorCollect() {
        Integer mid = UserUtil.getMerchantId();
        List<Anchor> data = anchorMapper.anchorCollect(mid);
        data.forEach(a -> a.setCateList(categoryHandler(a.getCategory())));
        return new BaseResult<>(data.size(), data);
    }

    @Override
    public BaseResult<?> anchorAddWx(Integer id) {
        Integer mid = UserUtil.getMerchantId();
        anchorMapper.anchorAddWx(mid, id);
        return new BaseResult<>();
    }

    @Override
    public BaseResult<?> anchorRemove(Integer id) {
        Integer mid = UserUtil.getMerchantId();
        anchorMapper.anchorRemove(mid, id);
        return new BaseResult<>();
    }

    @Override
    public BaseResult<?> anchorFollow(Integer id) {
        Integer mid = UserUtil.getMerchantId();
        List<ContentVO> data =  contentMapper.contentListParam(mid, id, 1);
        return new BaseResult<>(data);
    }

    @Override
    public BaseResult<?> anchorFollowAdd(Integer id, String content) {
        Integer mid = UserUtil.getMerchantId();
        contentMapper.insContent(mid, id, content, 1);
        return new BaseResult<>();
    }

}
