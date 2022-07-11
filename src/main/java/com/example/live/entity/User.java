package com.example.live.entity;

import lombok.Data;

/**
 * 后台用户、商户用户
 * @author baishuailei@zhejianglab.com
 * @date 2022/7/6
 */
@Data
public class User {
    private int id;
    private String mobile;
    private String pwd;
    // 备注
    private String remark;
    // 等级：超级管理员-1、管理员（代理）-2、业务员-3
    private int level;
    // 微信二维码,可支持16mb
    private String wx;
    // 创建时间
    private String ct;
    // 更新时间
    private String ut;

}
