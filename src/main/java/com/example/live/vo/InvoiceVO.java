package com.example.live.vo;

import lombok.Data;

/**
 * @author baishuailei@zhejianglab.com
 * @date 2022/7/14 11:01
 */
@Data
public class InvoiceVO {

    private Integer id;
    //手机号
    private String mobile;
    // 业务员
    private Integer opeUser;
    // 发票金额
    private Double money;
    // 状态：未开-0、已开-1、驳回-2
    private String status;
    //备注
    private String remark;
    // 发票公司名称
    private String company;
    // 纳税识别号
    private String tax;
    // 电子邮箱
    private String email;
    // 申请时间
    private String ct;
    // 操作时间
    private String ut;
}
