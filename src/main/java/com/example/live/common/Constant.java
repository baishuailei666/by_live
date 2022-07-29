package com.example.live.common;

import java.util.HashMap;
import java.util.Map;

/**
 * 通用参数
 *
 * @author baishuailei@zhejianglab.com
 * @date 2022/07/06
 */
public class Constant {

    public static final String name = "杭州太博文化传媒有限公司";

    // 超级管理员user_id=11
    public static final int admin_id = 11;

    // session最大过期时间
    public static final int interval = 3600;

    public static final String session_user = "system_user";
    // 默认密码
    public static final String defaultPwd = "123456";

    // source：back-管理端、merchant-商户端
    public static final String source_back = "back";
    public static final String source_merchant = "merchant";

    // 购买服务说明
    public static final String buy_subject = "直播推广服务";

    // 订单支付成功
    public static final String pay_success = "TRADE_SUCCESS";

    // 定义分隔符
    public static final String split = ",";
    public static final String split2 = ";";
    public static final String split3 = "-";
    // 发送邮件主题
    public static final String email_subject = buy_subject+"-开票信息";

    // 腾讯云
    public static final String cloud_url = "https://taibo-culture-1313027383.cos.ap-shanghai.myqcloud.com";
    public static final String cloud_secretId = "AKIDLWTBdqDMGvKYWHm35sPiogIfoiMlvqVu";
    public static final String cloud_secretKey = "DBtMaaLq55sRlqfFZt52kDRQAetteARS";

    /**
     * 支付宝支付
     */
    // 签名方式
    public static final String sign_type = "RSA2";

    // 字符编码格式
    public static final String charset = "utf-8";

    public static final String alipay_app_id = "2021003141677094";

    public static final String alipay_private_key = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCmOG1ONv5ZtDTC2d+rM8fV4Gd0kfntksLhe/Cr4G7zN+wT6OlBciTMdxmoJQxyLdg+PyVtaOzu0Ru0b8AE9agM7fBrtkAYTCq3EmbWJ1MWVo0xRMhduxDDfihytvyMKcgEGWofoYHrhLGpHyeV9erPPSudvaO+6R34qb70PmK75YOBNd8b2Jqwqxww9s0jMMh6YzURhVgzCvO2HWApj4DGDtymrTEp8CDbGdn44QMEy6tGxyxHRVw6hbzrZ3CzSpJBZptQn9jbJMOtCmVijTb8xOE7F7+KD+t/Jc8nwBFltCGwSu1HSqSjG+PJPSLuQiCBYtyrYfuaEXR+mP9i5ztvAgMBAAECggEAHPGudvQ0jkJG/bcar6EwpxmQakgREUGBFeqTGhrWtxuBzGW+WFsb9U639QOeizNYeF5ekhK3T06yWFUtRq/QN5AnUVpHBvQcbAyUQ65NVZJ0u6OSkF/Gp4QMXw1mJLIR1kTmqeNh0DUnCqAWMJLF9JheQ/WF0fSd93L3gWhroPQqfmbYpuseLcSa9s7IF6B37kTkW1NAs91yUAlFM0PsnA+3lmhjbepvMBYCAeUugEiyiR+t4xXtpf02Av50p1OoF3YBX852T2KmN6BAkDl09xiPe4JelKORtEnJeQzPwEt+0lQWEaUCK51eq4yFd5LueNc2gVceoUlfK8owriRHIQKBgQDPd5bKYMdVNT65TqhTKGALIHbkcDDMI/XxYOokTv0apospC99UVxIwoGJG0fcsGr81C1rzPv6ykGq1WR/0d74eLz0/ivisQ5X+8/0F+e2YXsNM5LosQIba/6Z9eNnIy6grx0AYvkk6CkgWe5z3ORvcj5Gsi7OLJ3FSpeXJXtylUwKBgQDNGrrULNm0MG+wmnu5olQ0RaAqOIjE97Q7MD2dYN9j4VL8avqBySJgscMaYKDKUbR2jNkWbJkyryfzYKXjpsToHJzpgUhIL/zU0r/XdPYAxQEppY5AfRT9qGz5gSz5E3fs/HLRiQa28X2zFNg6sL5m2s71m3j19QDZ21yFa2OR9QKBgHRduGdFBykk10yq8MDL2asEB4uz3bryxKUh3dKFt3N2TIG2VgqBWAOMQfNFG6VST0MaXmYnREa1uBSFq+W8MBfQGUOF2FsPAZfiFF6Aw6J+LH75n+7oNnupfyHLYiQ9DtOXS71VJc93Kt3pca49CwKYSHYGWatIcUiRIqTjH1FfAoGAO1se8kRAhIr3563tXF2D7NSxc0aFHitpNy/QxPjB7U/KCk6TTHf+wOjww3NvVN2sUhqUwI6RDz/tWduAZo9esKWHK3REjM+GZbjP1/R7NOHOYlUNA9CvAHF7iAFAGJzvud+dAtKa5NqiO+8uXF3F3FI6To29xcAvjNyUu+/GEvkCgYBXyY41By0Enz0sHpXoNB09YeXTRvCxUtdyutJBCuKMkL7AUpyfTloXLKuOCPN0Mk+UN7M27nXt7DvVTPjN1dbupEOOHy911oW1OTHkjSDc7o+Agk7RxLMliEEeaYxOIpYe3Mp3pDjO00mDusjpGpKkdnT2WaAJMrVw1a+NXNw75A==";

    public static final String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAotFaQxQnLdymH6kxSVjQfxmkK3Bh9hq85uVPUpCH5CEoiwi7GhF1ETUpqdPBJ9Ha03uiz2V80UM7PWU3Clo1R4a1z9A7ZvZNEb+0fNTjU/jQkkrTQ4INGZL3hEbaA+W31tPlr9Keq9gPbzi2ol8dupzHzvr/LIRNMeiBFrkWxw4bmgdbEFVrIXZciKwQr9aO0LIoOzuc/2XPwk3VEDPaRAzwZ26RbHd4Rr5cJhNi523yOpE5G1EQ3IqhlziubTr2c17c/cauBv3mgt2YMTQ2b7x7h1ZEYZCR/MueMpDQz5jUDA7iZO+bE4kCPbyu+jTb/FMdpDySjHzFPJU2iqUzmwIDAQAB";

    // 支付宝网关
    public static String gatewayUrl = "https://openapi.alipay.com/gateway.do";

    // 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static final String notify_url = "http://f2y7g8.natappfree.cc/pay/notify/ali";

    // 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static final String return_url = "http://43.142.141.181:8000";


    /**
     * 微信支付
     */
    // 通知URL必须为直接可访问的URL，不允许携带查询串，要求必须为https地址
    public static final String notify_url2 = "http://f2y7g8.natappfree.cc/pay/notify/wx";

    //
    public static Map<Integer, String> levelTypeMap;
    static {
        levelTypeMap = new HashMap<>();
        levelTypeMap.put(1, "超级管理员(唯一)");
        levelTypeMap.put(2, "管理员(代理商)");
        levelTypeMap.put(3, "业务员");
    }
    // 购买类型数据转换
    public static Map<Integer, String> buyTypeMap;
    static {
        buyTypeMap = new HashMap<>();
        buyTypeMap.put(1, "月卡");
        buyTypeMap.put(2, "季卡");
        buyTypeMap.put(3, "年卡");
    }
    // 支付类型数据转换
    public static Map<Integer, String> payTypeMap;
    static {
        payTypeMap = new HashMap<>();
        payTypeMap.put(1, "支付宝");
        payTypeMap.put(2, "微信");
        payTypeMap.put(3, "对公转账");
    }
    // 跟进记录-1、备注-2、消息通知-3
    public static Map<Integer, String> contentTypeMap;
    static {
        contentTypeMap = new HashMap<>();
        contentTypeMap.put(1, "跟进记录");
        contentTypeMap.put(2, "备注");
        contentTypeMap.put(3, "消息通知");
    }
    // 签署类型：0-企业签署、1-个人签署
    public static Map<Integer, String> signTypeMap;
    static {
        signTypeMap = new HashMap<>();
        signTypeMap.put(0, "企业签署");
        signTypeMap.put(1, "个人签署");
    }

    // 未开-0、已开-1、驳回-2
    public static Map<Integer, String> invoiceStatusMap;
    static {
        invoiceStatusMap = new HashMap<>();
        invoiceStatusMap.put(0, "未开");
        invoiceStatusMap.put(1, "已开");
        invoiceStatusMap.put(2, "驳回");
    }
    // 意向程度：未联系-0、跟进中-1、已处理-2、已拒绝-3
    public static Map<Integer, String> intentionMap;
    static {
        intentionMap = new HashMap<>();
        intentionMap.put(0, "未联系");
        intentionMap.put(1, "跟进中");
        intentionMap.put(2, "已处理");
        intentionMap.put(3, "已拒绝");
    }
    // 状态：待审核-0、审核通过-1、已拒绝-2
    public static Map<Integer, String> auditStatusMap;
    static {
        auditStatusMap = new HashMap<>();
        auditStatusMap.put(0, "审核中");
        auditStatusMap.put(1, "已通过");
        auditStatusMap.put(2, "已拒绝");
    }


}
