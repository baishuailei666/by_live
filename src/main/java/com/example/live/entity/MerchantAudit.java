package com.example.live.entity;

import lombok.Data;

/**
 * 商家店铺审核
 *
 * @author baishuailei@zhejianglab.com
 * @date 2022/7/6
 */
@Data
public class MerchantAudit {
    private int id;
    // 商家店铺id
    private Integer merchantId;
    // 业务员
    private Integer opeUser;
    // 状态：待审核-0、审核通过-1、已拒绝-2
    private int status;
    // 申请时间
    private String ct;
    // 操作时间
    private String ut;
    // 备注：驳回原因
    private String remark;

}
