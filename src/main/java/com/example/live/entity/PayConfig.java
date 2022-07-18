package com.example.live.entity;

import lombok.Data;

/**
 * 管理员（代理商）支付配置信息
 *
 * @author baishuailei@zhejianglab.com
 * @date 2022/7/9 15:16
 */
@Data
public class PayConfig {
    private int id;
    // 对应每个管理员（代理商）支付信息
    private Integer agentUser;
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
    // 电子签SecretId
    private String signSecretId;
    // 电子签SecretKey
    private String signSecretKey;
    // 电子签模板Id
    private String signTemplateId;

    // 创建时间
    private String ct;
    // 更新时间
    private String ut;
    // 支付凭证备注信息
    private String remark;

    // 管理员（代理商）手机、备注等
    private String mobile;
    private String agentRemark;

}
