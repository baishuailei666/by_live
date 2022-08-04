package com.example.live.entity;

import lombok.Data;

/**
 * 商家购买订单
 *
 * @author baishuailei@zhejianglab.com
 * @date 2022/7/6
 */
@Data
public class Order {
    private int id;
    // 订单号（yyyyMMddHHmm+4位随机数）
    private String orderNo;
    // 交易号
    private String tradeNo;
    // 商品店铺id
    private int merchantId;
    // 权益类型：月卡-1、季卡-2、年卡-3
    private int buyType;
    // 金额
    private Double money;
    // 业务员
    private Integer opeUser;
    // 支付类型：支付宝-1、微信-2、对公-3
    private int payType;
    private String status;
    // 合同签署id
    private String flowId;
    // 支付下单时间
    private String ct;
    // 支付成功时间
    private String ut;
}
