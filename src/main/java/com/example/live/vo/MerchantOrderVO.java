package com.example.live.vo;

import lombok.Data;

/**
 * 商户订单列表
 * @author baishuailei@zhejianglab.com
 * @date 2022/7/11 19:44
 */
@Data
public class MerchantOrderVO {

    // 订单号（yyyyMMddHHmm+4位随机数）
    private String orderNo;
    // 权益类型：月卡-1、季卡-2、年卡-3
    private String buyType;
    // 金额
    private Double money;
    // 支付类型：支付宝-1、微信-2、对公-3
    private String payType;
    // 支付成功时间
    private String ut;
}
