package com.example.live.vo;

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
    private String level;
    private String wx;
    private String ts;
    private List<String> apis;
}
