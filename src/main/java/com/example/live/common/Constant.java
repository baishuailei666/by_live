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

    // 超级管理员user_id=11
    public static final int admin_id = 11;

    public static final String session_user = "user";
    // 默认密码
    public static final String defaultPwd = "123456";
    // source：back-管理端、merchant-商户端
    public static final String source_back = "back";
    public static final String source_merchant = "merchant";

    // 微信支付证书路径：cert/agentUser/.p12文件
    public static final String cert_path = "/cert/agent-";

    // 购买服务说明
    public static final String buy_subject = "直播推广服务";

    // 订单支付成功
    public static final String pay_success = "TRADE_SUCCESS";

    // 定义分隔符
    public static final String split = ",";
    public static final String split2 = ";";
    // 发送邮件主题
    public static final String email_subject = "开票信息";

    // 存储链接
    public static final String cloud_url = "";


    // 商品链接：3545396053325007934
    public static final String goods_url = "https://haohuo.jinritemai.com/views/product/detail?id=";
    // 店铺链接：13939925088
    public static final String  shop_url = "https://haohuo.jinritemai.com/views/shop/index?id=";

    /**
     * 支付宝支付
     */
    // 签名方式
    public static final String sign_type = "RSA2";

    // 字符编码格式
    public static final String charset = "utf-8";

    public static final String alipay_public_key = "";

    // 支付宝网关
    public static String gatewayUrl = "https://openapi.alipay.com/gateway.do";

    // 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static final String notify_url = "/pay/notify/ali";

    // 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static final String return_url = "";


    /**
     * 微信支付
     */
    // 通知URL必须为直接可访问的URL，不允许携带查询串，要求必须为https地址
    public static final String notify_url2 = "/pay/notify/wx";


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
    // 签署类型：1-企业签署、2-个人签署
    public static Map<Integer, String> signTypeMap;
    static {
        signTypeMap = new HashMap<>();
        signTypeMap.put(1, "企业签署");
        signTypeMap.put(2, "个人签署");
    }
    // 签署状态：0-未签、1-已签
    public static Map<Integer, String> signStatusMap;
    static {
        signStatusMap = new HashMap<>();
        signStatusMap.put(0, "未签署");
        signStatusMap.put(1, "已签署");
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
}
