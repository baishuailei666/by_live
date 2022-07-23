package com.example.live.vo;

import lombok.Data;

/**
 * @author baishuailei@zhejianglab.com
 * @date 2022/7/23 13:48
 */
@Data
public class UserListVO {
    private int id;
    private String mobile;
    // 备注
    private String remark;
    // 等级：超级管理员-1、管理员（代理）-2、业务员-3
    private String level;
    // 微信二维码,可支持16mb
    private String wx;
    // 创建时间
    private String ct;
    // 更新时间
    private String ut;
}
