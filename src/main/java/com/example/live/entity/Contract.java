package com.example.live.entity;

import lombok.Data;

/**
 * 合同
 *
 * @author baishuailei@zhejianglab.com
 * @date 2022/7/6
 */
@Data
public class Contract {
    private int id;
    // 商户店铺id
    private Integer merchantId;
    // 业务员
    private Integer opeUser;
    // 权益类型：月卡-1、季卡-2、年卡-3
    private int buyType;
    // 提交时间
    private String ct;
    // 操作时间
    private String ut;
    // 签署状态：0-未签、1-已签
    private int signStatus;
    // 签署类型：0-企业签署、1-个人签署
    private int signType;
    // 签署成功合同id，可用于下载查看等
    private String documentId;
    // 合同名称
    private String documentName;
    // 签署合同flowId
    private String flowId;


    /**
     * merchant_sign
     */

    // 小店主体名称
    private String subject;
    // 签署人
    private String person;
    // 手机号码
    private String mobile;
    // 统一社会信用代码/身份证号码
    private String tax;

}
