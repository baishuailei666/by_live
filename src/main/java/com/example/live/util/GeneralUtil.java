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

    // 获取4位随机数
    public static String get4Random() {
        Random random = new Random();
        DecimalFormat df = new DecimalFormat("0000");
        return df.format(random.nextInt(10000));
    }
    public static String get6Random() {
        Random random = new Random();
        DecimalFormat df = new DecimalFormat("000000");
        return df.format(random.nextInt(1000000));
    }

    // 默认密码
    public static String defaultPwd() {
        return MD5Util.encode(Constant.defaultPwd);
    }

    // 自定义:page=1、size=10
    public static int indexPage(int page) {
        page = page == 0 ? 1 : page;
        return (page - 1) * 10;
    }

    public static int parseInt(Object num) {
        if (num == null) {
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
            case 1:
                days = 30;
                break;
            case 2:
                days = 90;
                break;
            case 3:
                days = 360;
                break;
        }
        return days;
    }

    // 服务购买剩余天数
    public static int buyDays(int type, String ut) {
        int buy_days = typeDays(type);
        long time_days = DateUtil.daysOfTime_2(ut, DateUtil.getTime());
        int days = (int) (buy_days - time_days);
        return Math.max(days, 0);
    }

    // 0-邮箱地址、1-客服电话、2-服务价格
    public static String[] getAgentConfig(String str, int type) {
        if (StringUtils.isBlank(str)) {
            return new String[]{"", ""};
        }
        // 逗号分隔：发件邮箱,收件邮箱;客服电话1,客服电话2;月卡,季卡,年卡
        String[] typeStr = str.split(Constant.split2);
        if (typeStr[type].contains(",")) {
            return typeStr[type].split(Constant.split);
        } else {
            return new String[]{"", "", ""};
        }
    }

    // 1-月卡、2-季卡、3-年卡
    public static String getOrderNo(int type) {
        int random = (int) ((Math.random() * 9 + 1) * 1000);
        // 年月日时分秒+0+购买类型
        return DateUtil.getTime2() + random + "0" + type;
    }

    /**
     * 对字符加星号处理：除前面几位和后面几位外，其他的字符以星号代替
     *
     * @param content  传入的字符串
     * @param frontNum 保留前面字符的位数
     * @param endNum   保留后面字符的位数
     * @return 带星号的字符串
     */
    public static String getStarString(String content, int frontNum, int endNum) {
        if (frontNum >= content.length() || frontNum < 0) {
            return content;
        }
        if (endNum >= content.length() || endNum < 0) {
            return content;
        }
        if (frontNum + endNum >= content.length()) {
            return content;
        }
        StringBuilder starStr = new StringBuilder();
        for (int i = 0; i < (content.length() - frontNum - endNum); i++) {
            starStr.append("*");
        }
        return content.substring(0, frontNum) + starStr
                + content.substring(content.length() - endNum);
    }

    //推广类目格式更改
    public static String getCategory(String s) {
        StringBuilder category = new StringBuilder();
        if (StringUtils.isNotEmpty(s)) {
            String[] split = s.split(Constant.split2);
            for (String s1 : split) {
                int i = s1.indexOf("=");
                String substring = s1.substring(0, i);
                category.append(substring).append("，");
            }
            category.deleteCharAt(category.length() - 1);
        }
        return category.toString();
    }

    //视频格式判断
    public static Boolean videoFormat(String s) {
        if (StringUtils.isEmpty(s)) {
            return false;
        }
        if (!s.contains(".")) {
            return false;
        }
        String[] split = s.split("\\.");
        String format = split[split.length - 1];
        switch (format) {
            case "mp4":
            case "avi":
            case "flv":
            case "mpg":
            case "wmv":
            case "ram":
                return true;
            default:
                return false;
        }
    }

    public static String opeUserHandler(Integer id, String remark, String mobile) {
        return id+Constant.split4+remark+Constant.split4+mobile;
    }

}
