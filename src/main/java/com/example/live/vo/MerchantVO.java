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
    private Integer opeUser;
    private String buyType;
    private Integer days;
    private String ct;
    private String lt;

}
