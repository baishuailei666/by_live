package com.example.live.vo;

import lombok.Data;

/**
 * 商户端系统：
 * 个人中心
 *
 * @author baishuailei@zhejianglab.com
 * @date 2022/7/10 14:23
 */
@Data
public class MerchantVO {
    private int id;
    private String mobile;
    private String shopId;
    private String shop;
    private String shopStatus;
    private String opeUserRemark;
    private Integer opeUser;
    // 对应业务员微信
    private String opeUserWx;
    private String buyType;
    private int vipType;
    // 剩余天数
    private Integer days;
    private Integer loginCount;
    private String ct;
    private String lt;

}
