package com.example.live.vo;

import lombok.Data;

/**
 * @author baishuailei@zhejianglab.com
 * @date 2022/7/14 11:01
 */
@Data
public class ContractVO {
    private int id;
    private Integer merchantId;
    private String shop;
    // 业务员
    private String opeUser;
    // 权益类型：月卡-1、季卡-2、年卡-3
    private String buyType;
    // 提交时间
    private String ct;
    // 操作时间
    private String ut;
    // 备注
    private String remark;
    // 合同文档id
    private String documentId;
    private String documentName;
    // 签署状态：0-未签、1-已签
    private String signStatus;
    // 签署类型：1-企业签署、2-个人签署
    private String signType;
    // 店铺公司名称
    private String company;
    // 统一社会信用代码/签署人姓名
    private String tax;
    // 法定代表人/签署人身份证号
    private String owner;
    // 签署人手机号
    private String mobile;
}
