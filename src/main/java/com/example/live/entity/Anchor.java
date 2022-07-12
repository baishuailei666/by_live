package com.example.live.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 主播
 *
 * @author baishuailei@zhejianglab.com
 * @date 2022/7/8 22:23
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Anchor extends BaseRowModel {
    @ExcelProperty(value = "序号",index = 0)
    private int id;
    // 昵称
    @ExcelProperty(value = "昵称",index = 1)
    private String nickname;
    // 头像
    @ExcelProperty(value = "头像",index = 2)
    private String img;
    // 抖音号id
    @ExcelProperty(value = "抖音号ID",index = 3)
    private String authorId;
    // 性别：男-1、女-2
    @ExcelProperty(value = "性别(男-1、女-2)",index = 4)
    private Integer gender;
    // 账号等级：1、2、3、4、5
    @ExcelProperty(value = "账号等级(1、2、3、4、5)",index = 5)
    private Integer level;
    // 粉丝
    @ExcelProperty(value = "粉丝",index = 6)
    private String fans;
    // 主页链接
    @ExcelProperty(value = "主页链接",index = 7)
    private String url;
    // 推广类目（类目=数量;类目=数量）
    @ExcelProperty(value = "推广类目(类目:数量,类目:数量)",index = 8)
    private String category;
    // 类目
    private List<JSONObject> cateList;
    // 推广商品
    @ExcelProperty(value = "推广商品",index = 9)
    private Integer goods;
    // 橱窗销量
    @ExcelProperty(value = "橱窗销量",index = 10)
    private Integer sales;
    // 简介
    @ExcelProperty(value = "简介",index = 11)
    private String introduce;
    // 直播带货场次：28
    @ExcelProperty(value = "直播带货场次",index = 12)
    private Integer live;
    // 直播带货天数：18
    @ExcelProperty(value = "直播带货天数",index = 13)
    private Integer liveDay;
    // 直播间观看人数：9517
    @ExcelProperty(value = "直播间观看人数",index = 14)
    private Integer view;
    // 平均观看时长：124
    @ExcelProperty(value = "平均观看时长",index = 15)
    private Integer avgDur;
    // 转化指数：85.4
    @ExcelProperty(value = "转化指数",index = 16)
    private String rate;
    // 场均销售额：1k-5k
    @ExcelProperty(value = "场均销售额",index = 17)
    private String avgGmv;
    // 主推价格区间：0-50
    @ExcelProperty(value = "主推价格区间",index = 18)
    private String price;
    // 带货口碑：4.09
    @ExcelProperty(value = "带货口碑",index = 19)
    private String score;


}
