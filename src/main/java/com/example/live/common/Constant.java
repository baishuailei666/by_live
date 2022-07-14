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

    // 购买服务说明
    public static final String buy_subject = "直播推广服务";

    // 订单支付成功
    public static final String pay_success = "TRADE_SUCCESS";

    public static final String split = ",";
    public static final String split2 = ";";
    // 发送邮件主题
    public static final String email_subject = "开票信息";

    // 商品链接：3545396053325007934
    public static final String goods_url = "https://haohuo.jinritemai.com/views/product/detail?id=";
    // 店铺链接：13939925088
    public static final String  shop_url = "https://haohuo.jinritemai.com/views/shop/index?id=";

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
}
