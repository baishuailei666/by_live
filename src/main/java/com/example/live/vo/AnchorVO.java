package com.example.live.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import java.util.List;

/**
 * @author baishuailei@zhejianglab.com
 * @date 2022/7/13 16:39
 */
@Data
public class AnchorVO {
    private int id;
    // 昵称
    private String nickname;
    // 头像
    private String img;
    // 粉丝
    private String fans;
    // 推广类目（类目=数量;类目=数量）
    private List<String> category;
    // 推广商品
    private Integer goods;
    // 橱窗销量
    private Integer sales;

}
