package com.example.live.vo;

import lombok.Data;

/**
 * 配置列表
 *
 * @author baishuailei@zhejianglab.com
 * @date 2022/7/13 9:43
 */
@Data
public class DataConfigVO {
    private Integer id;
    private String ct;
    // 逗号分隔：发件邮箱,收件邮箱;客服电话1,客服电话2;月卡,季卡,年卡
    private String emailReceive;
    private String emailSend;
    private String kef1;
    private String kef2;

    private String monthCard;
    private String seasonCard;
    private String yearCard;

}
