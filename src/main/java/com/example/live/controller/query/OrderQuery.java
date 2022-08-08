package com.example.live.controller.query;

import lombok.Data;

import java.util.List;

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

    private List<Integer> opeUserIds;
    private String orderNo;
    // 购买类型：1-月卡、2-季卡、3-年卡
    private Integer buyType;
    // 支付状态：0-未支付、1-已支付
    private Integer payStatus;

}
