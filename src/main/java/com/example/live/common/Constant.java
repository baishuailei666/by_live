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
}
