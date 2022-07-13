package com.example.live.vo;

import lombok.Data;

/**
 * @author baishuailei@zhejianglab.com
 * @date 2022/7/13 10:43
 */
@Data
public class PayConfigVO {
    private int id;
    // 对应每个管理员（代理商）支付信息
    private Integer agentUser;
    private String mobile;
    private String agentRemark;
    // 支付宝支付，appid
    private String aliAppId;
    // 支付宝支付，私钥
    private String aliPrivateKey;
    // 支付宝支付，公钥
    private String aliPublicKey;
    // 微信支付，appid
    private String wxAppId;
    // 微信支付，商户号
    private String wxMchId;
    // 微信支付，商户密钥
    private String wxMchKey;
    // 微信支付，认证证书apiclient_cert.p12文件的绝对路径，或者如果放在项目中，请以classpath:开头指定
    private String wxKeyPath;
    // 对公公司
    private String contrary;
    // 对公银行
    private String contraryBank;
    // 对公账户
    private String contraryAccount;
    // 创建时间
    private String ct;
    // 更新时间
    private String ut;
    private String remark;
}
