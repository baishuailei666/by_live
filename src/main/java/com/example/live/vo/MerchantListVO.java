package com.example.live.vo;

import lombok.Data;

/**
 * @author baishuailei@zhejianglab.com
 * @date 2022/8/12 15:07
 */
@Data
public class MerchantListVO {
    private int id;
    private String mobile;
    private String shopId;
    private String shop;
    private String shopStatus;
    private String opeUser;
    private String buyType;
    // 剩余天数
    private Integer days;
    private Integer loginCount;
    private String ct;
    private String lt;
}
