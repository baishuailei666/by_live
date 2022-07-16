package com.example.live.entity;

import lombok.Data;

/**
 * 等级权限
 *
 * @author baishuailei@zhejianglab.com
 * @date 2022/7/8 20:56
 */
@Data
public class LevelRight {
    private int id;
    // 等级：超级管理员-1、管理员（代理）-2、业务员-3、商户端登录商户-0
    private int level;
    // 服务接口
    private String api;
    // 说明
    private String note;
}
