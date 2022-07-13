package com.example.live.entity;

import lombok.Data;

/**
 * 配置
 * @author baishuailei@zhejianglab.com
 * @date 2022/7/10 9:21
 */
@Data
public class DataConfig {
    private int id;
    // 管理员（代理商）
    private int agentUser;
    // 逗号分隔：发件邮箱,收件邮箱;客服电话1,客服电话2;月卡,季卡,年卡
    private String content;
    private String ct;


    private String mobile;
    private String agentRemark;
}
