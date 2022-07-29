package com.example.live.entity;

import lombok.Data;

/**
 * 商户（通过推广链接进行注册登录）
 *
 * @author baishuailei@zhejianglab.com
 * @date 2022/7/6
 */
@Data
public class Merchant {
    private int id;
    // 手机号
    private String mobile;
    // 商户端登录密码
    private String pwd;
    // 店铺id，只能绑定一个
    private String shopId;
    // 店铺名称
    private String shop;
    // 商品链接
    private String goods;
    // 商家介绍（<140字）
    private String introduce;
    // 业务员
    private Integer opeUser;
    // 店铺状态：未认证、已认证、购买等级（月卡、季卡、年卡）
    private String shopStatus;
    // 会员剩余天数（<7天标红）
    private int days;
    // 登录次数
    private int loginCount;
    // 登录时间
    private String lt;
    // 创建时间
    private String ct;


    // 店铺审核状态：已认证、审核中、未通过
    private String auditStatus;
    // 审核备注
    private String auditRemark;
    //
    private String opeUserWx;

}
