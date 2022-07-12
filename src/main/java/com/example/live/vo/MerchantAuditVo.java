package com.example.live.vo;

import lombok.Data;

/**
 * @Author Chen Rui
 * @Date 2022/7/11 21:32
 * @Description 审核
 */
@Data
public class MerchantAuditVo {

    private Integer merchantId;
    private String mobile;
    private String shop;
    private Integer opeUser;
    private String status;
    private String introduce;
    private String ct;
    private String ut;
    private String remark;

}
