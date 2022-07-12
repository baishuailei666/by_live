package com.example.live.contorller.query;

import lombok.Data;

/**
 * 后台：订单列表查询
 *
 * @author baishuailei@zhejianglab.com
 * @date 2022/7/11 21:51
 */
@Data
public class OrderQuery {
    private Integer page;
    private String mobile;
    private String shop;
    private String start;
    private String end;
    private Integer opeUser;
    private String orderNo;
    // 购买类型：1-月卡、2-季卡、3月卡
    private Integer buyType;
    // 支付状态：0-未支付、1-已支付
    private Integer payStatus;
}
