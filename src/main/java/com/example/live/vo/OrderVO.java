package com.example.live.vo;

import lombok.Data;

/**
 * @author baishuailei@zhejianglab.com
 * @date 2022/7/13 15:19
 */
@Data
public class OrderVO {
    private int id;
    // 订单号（yyyyMMddHHmm+4位随机数）
    private String orderNo;
    // 交易号
    private String tradeNo;
    // 商品店铺id
    private int merchantId;
    private String mobile;
    private String shop;
    // 权益类型：月卡-1、季卡-2、年卡-3
    private String buyType1;
    // 金额
    private Double money;
    // 业务员
    private String opeUser;
    // 支付类型：支付宝-1、微信-2、对公-3
    private String payType1;
    // 支付状态
    private String payStatus;
    // 支付下单时间
    private String ct;
    // 支付成功时间
    private String ut;
}
