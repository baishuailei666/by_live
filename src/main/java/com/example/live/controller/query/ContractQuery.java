package com.example.live.controller.query;

import lombok.Data;

import java.util.List;

/**
 * @author baishuailei@zhejianglab.com
 * @date 2022/7/14 10:42
 */
@Data
public class ContractQuery {
    // 商户手机号
    private String mobile;
    private String shop;
    private String company;
    private String start;
    private String end;
    // 权益类型：月卡-1、季卡-2、年卡-3
    private Integer buyType;
    // 业务员
    private List<Integer> opeUserIds;
    private Boolean admin11;
    private Integer page;
}
