package com.example.live.vo;

import com.example.live.entity.LevelRight;
import lombok.Data;

import java.util.List;

/**
 * 后台管理系统：
 * 个人中心
 * @author baishuailei@zhejianglab.com
 * @date 2022/7/6
 */
@Data
public class UserVO {
    private int id;
    private String mobile;
    // 等级：超级管理员-1、管理员（代理）-2、业务员-3
    private Integer level;
    private String wx;
    private String ts;
    private Integer agentUser;
    private List<LevelRight> rights;
}
