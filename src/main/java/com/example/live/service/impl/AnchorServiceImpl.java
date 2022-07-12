package com.example.live.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.example.live.common.BaseResult;
import com.example.live.common.Constant;
import com.example.live.entity.Anchor;
import com.example.live.mapper.AnchorMapper;
import com.example.live.service.AnchorService;
import com.example.live.util.UserUtil;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
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

    private List<JSONObject> categoryHandler(String category) {
        if (StringUtils.isBlank(category)) {
            return null;
        }
        List<JSONObject> joList = Lists.newArrayList();
        if (category.contains(Constant.split2)) {
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
        Anchor anchor = anchorMapper.anchorInfo(id);
        if (anchor!=null) {
            String category = anchor.getCategory();
            anchor.setCateList(categoryHandler(category));
        }
        return new BaseResult<>(anchor);
    }

    @Override
    public BaseResult<?> anchorList() {
        List<Anchor> data = anchorMapper.anchorList();
        data.forEach(a -> a.setCateList(categoryHandler(a.getCategory())));
        return new BaseResult<>(data.size(), data);
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

}
