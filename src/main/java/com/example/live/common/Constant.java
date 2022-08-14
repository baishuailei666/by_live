package com.example.live.common;

import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 通用参数
 *
 * @author baishuailei@zhejianglab.com
 * @date 2022/07/06
 */
public class Constant {

    public static final String base_url = "http://taizb.com";

    public static final String name = "杭州太博文化传媒有限公司";

    // 超级管理员user_id=11
    public static final int admin_id = 11;

    // session最大过期时间
    public static final int interval = 3600;

    public static final String session_user = "back_user";
    public static final String session_user2 = "merchant_user";
    // 默认密码
    public static final String defaultPwd = "123456";

    // source：back-管理端、merchant-商户端
    public static final String source_back = "back";
    public static final String source_merchant = "merchant";

    // 临时文件存放的文件夹
    public final static String TEMPORARY = "temporary";

    // 购买服务说明
    public static final String buy_subject = "直播推广服务";

    // 订单支付成功
    public static final String pay_success = "TRADE_SUCCESS";

    // 定义分隔符
    public static final String split = ",";
    public static final String split2 = ";";
    public static final String split3 = "-";
    public static final String split4 = "/";
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
    public static final String notify_url = base_url+"/pay/notify/ali";

    // 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static final String return_url = base_url+"/workbench";

    /**
     * 微信支付
     */
    // 通知URL必须为直接可访问的URL，不允许携带查询串，要求必须为https地址
    public static final String notify_url2 = base_url+"/pay/notify/wx";

    // 主播默认头像
    public static final String anchor_img = "data:image/webp;base64,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";

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
    public static Map<Integer, String> signStatusMap;
    static {
        signStatusMap = new HashMap<>();
        signStatusMap.put(0, "未签");
        signStatusMap.put(1, "已签");
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
