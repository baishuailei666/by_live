package com.example.live.util;

import com.example.live.common.Constant;
import org.apache.commons.lang3.StringUtils;

import java.text.DecimalFormat;
import java.util.Random;

/**
 * 通用方法util
 *
 * @author baishuailei@zhejianglab.com
 * @date 2022/07/06
 */
public final class GeneralUtil {

    public static void main(String[] args) throws Exception {
        System.out.println("***GeneralUtil main***");

    }

    private static final String random1 = "abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String random2 = "abcdefghijklmnopqrstuvwxyz0123456789";
    private static final String random3 = "abcdefghijklmnopqrstuvwxyz";


    // 自定义随机字符串
    public static String getRandomStr(int len, int r) {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        String base;
        if (r==1) {
            base = random1;
        } else if (r==2) {
            base = random2;
        } else {
            base = random3;
        }

        for (int i = 0; i < len; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    // 获取4位随机数
    public static String get4Random() {
        Random random = new Random();
        DecimalFormat df = new DecimalFormat("0000");
        return df.format(random.nextInt(10000));
    }

    // 默认密码
    public static String defaultPwd() {
        return MD5Util.encode(Constant.defaultPwd);
    }

    // 自定义:page=1、size=10
    public static int indexPage(int page) {
        page = page ==0 ? 1 : page;
        return (page -1) * 10;
    }
    public static int parseInt(Object num) {
        if (num ==null) {
            return 0;
        }
        if (num.equals("")) {
            return 0;
        }
        try {
            String result = num.toString().split("\\.")[0];

            return Integer.parseInt(result);
        } catch (Exception e) {
            return 0;
        }
    }

    // 服务天数
    public static int typeDays(int type) {
        int days = 0;
        switch (type) {
            case 1: days = 30;
                break;
            case 2: days = 90;
                break;
            case 3: days = 360;
                break;
        }
        return days;
    }

    // 服务购买剩余天数
    public static int buyDays(int type, String ut) {
        int buy_days = typeDays(type);
        long time_days = DateUtil.daysOfTime_2(ut, DateUtil.getTime());
        return (int) (buy_days - time_days);
    }

    // 0-邮箱地址、1-客服电话、2-服务价格
    public static String[] getAgentConfig(String str, int type) {
        if (StringUtils.isBlank(str)) {
            return new String[]{"",""};
        }
        // 逗号分隔：发件邮箱,收件邮箱;客服电话1,客服电话2;月卡,季卡,年卡
        String[] typeStr = str.split(Constant.split2);
        if (typeStr[type].contains(",")) {
            return typeStr[type].split(Constant.split);
        } else {
            return new String[]{"","",""};
        }
    }

    // 1-月卡、2-季卡、3-年卡
    public static String getOrderNo(int type) {
        int random = (int)((Math.random()*9+1)*1000);
        // 年月日时分秒+0+购买类型
        return DateUtil.getTime2() + random + "0" +type;
    }

    //获取分页索引
    public static int getIndexPage(int page){
            return (page - 1) * 10;
    }

}
