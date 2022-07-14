package com.example.live.vo;

import lombok.Data;

/**
 * @Author Chen Rui
 * @Date 2022/7/11 21:32
 * @Description 审核
 */
@Data
public class MerchantAuditVO {
    private Integer merchantId;
    private String mobile;
    private String shop;
    private String shopLink;
    private String goodsLink;
    private Integer opeUser;
    // 状态：待审核-0、审核通过-1、已拒绝-2
    private String status;
    private String introduce;
    private String ct;
    private String ut;
    private String remark;

}
