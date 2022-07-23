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

    public static final String session_user = "user";
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

    public static final String alipay_app_id = "2021003141677094";

    public static final String alipay_private_key = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCak97YPR7/SA559qVjyc/npuRBnzePkcdmDroJqjhmisCNgaWuLChDmHPEhCGcXB3cXhrA30/PRJhCxgFfmM5a2+WopUcRSnqitMpi1IzQhmtSbVjWQtES50iscowS+iTVvYF2qKkzsZn0234RHthf2bZTEYnq7JAnTEIarswmYLBAep7ftZFbhmrVcs7d59pCPRknQF1lA40Tu8csFMtdZakUqupN/krf/LCnN2ijgoJtRE76TQ4JM4MPnbMu9beVKmJtcal4YOjV5r5URYWpgqGytr0MR2eBxqjdUulPACDym9y0NQ6KH+WZlPhvVR0j3An7OCPiGmn6S95XC2SjAgMBAAECggEAd+kTqp0naZLlH/mUbSeFr0cItFslh6rniqUL7ils3SbvnhYfh1Bb36voyl5RkHDo/STHDi1tRPUKRGpDcHwiP9treXrU+XXUUll60lXd8nkD0gpfKr7kXwyplJS7UpFXd7MtNfY6Y453RrnqPq+13A0QYbouSd4/KSz/7qEAXxfU/52SQc+DbOlhScie0M0BThOmlQSyQ4Hi42KQlo0pl3X+xvUFbms5O4DkFXRzTs7veaUSN1NpXFRvgnXQvf+k3ZQF2mBASechme8UWSR8mJRKnfCe0nD0m6U0PR9TkGOzkGAXifBmapTg/1J8Ox/pZeFHinHqh2KjEUPAallRGQKBgQDiolhYafeBVTSNiBz10HoJm8IW+Q+sciZPq+VusjF8oZbuHpWWTFtrSwmMxZilI/DH4DSTl1J5oEm5dOYF28Sc1PDROmQItJwVK1JklldwQw6O/U6vfghikbGU8/vA70TgSc2it+4gOrfay1tyo1fC4+DANpNFAMSSX7BDg0eRzQKBgQCum1isp1AmxeBaZfbmgVUCdgtRVc7im5kh9z26zH3YsXd5UZRWMxyeVpMI2+wdgGn7Z8fmtcohMuNp0xacQrVrB+K84WMMA+D3vD2Y85RAY1JnmyZvvh1jDGIAL47GxGUXHbi9jY6E1OGLx2d5T40y++70Pxw3nUZM5DKV/2YgLwKBgB/xS8xiWx0iej68gYBv+tNh3bBJOFlQ2W9/Rd4dRXZQfCphqym852Gu+NCbhRE5iJVTV3W57UjxO5LlFAqdWA/Wv35t4W2MrzcYnQtKWVacTPAAade4RsxFxqa84K3ny2hlXpNhozFttT4kTWZR+WuhnKraxlUCb7JFxF0voqW9AoGAN17+NOpuKY2QwzoYnSqgecJTfnyWuhgb7MCKK+XTdtd4itmLD/bveWr8/882RnjFoztbElfN/M81fQa64dzpyUJb7ggPr+y+9/ETG6EK/4w/wqlZ/A+vJAz2OxbZ0UDQyg7b2gi9DHSx/fDDaJ4IqgVo1r34xc4zUAkB3b17dnMCgYEA4EJvRYrdSAIArwQmnfFhWslm3bP/e9ECiBayqOGOTqmEy/+cTAApOLXNI4rQ2jpkBywDarrBIyeBirro2cK7EQ1lNF4MCm8Y16Hoep+Fav2caD2uTelzlzyGQwd6BM7JHPIrlMln5alrLGW0PXWgO0dyxYacrE50vG2zUsM+xfU=";

    public static final String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAiszDTxJAoYnQBCCHNK8SVFjTSRMOMMw72JQUnjUofuilYGu3SvDpde+mY6hxQxEg9EAb21uVo9wDcbNqfPK+C/Dy2FIiBnAlVG6CCUEVT/Vtii922TttEUJnm1RljgBwJWC7i610fa+2KEJO2misLLoqKUOsBr4IPPcmpUau4AaTioWtDMmumMQNAh9+f/Ch0sobH8rE4BmdZdO8khTyQve8AskdH0Lkksm6oYlaxrJnZOrhxqe4aB/16hYRF0ee955ieUQgLYf27cIfnAeCOCBnNpEeYyY8znnUQpyystXkmNrRqpXm9FR2hz5d924EZmK5GxOxJC4joau87dkqNwIDAQAB";

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
    // 状态：待审核-0、审核通过-1、已拒绝-2
    public static Map<Integer, String> auditStatusMap;
    static {
        auditStatusMap = new HashMap<>();
        auditStatusMap.put(0, "待审核");
        auditStatusMap.put(1, "已通过");
        auditStatusMap.put(2, "已拒绝");
    }


}
