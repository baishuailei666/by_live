package com.example.live.util;

import com.example.live.common.Constant;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
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
        System.out.println(isLetterDigit("9122022NA6CY239G是"));
        
//        for (int i = 0; i < 150; i++) {
//            System.out.println(getIntRandom(13));
//        }
    }

    // 格式校验
    public static boolean isLetterDigit(String str) {
        if (StringUtils.isBlank(str)) {
            return false;
        }
        String regex = "^[a-z0-9A-Z]+$";
        return str.matches(regex);
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
    public static int getIntRandom(int len) {
        int i = (int)(Math.random()*(len +1));
        if (i>=len) {
            return getIntRandom(len-1);
        }
        return i;
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
    public static int index2Page(int page, int size) {
        page = page == 0 ? 1 : page;
        return (page - 1) * size;
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
    public static int typeMonths(int type) {
        int v = 1;
        switch (type) {
            case 1:
                v = 1;
                break;
            case 2:
                v = 3;
                break;
            case 3:
                v = 12;
                break;
        }
        return v;
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
    // id/昵称/手机
    public static String opeUserHandler(Integer id, String remark, String mobile) {
        return id+Constant.split4+remark+Constant.split4+mobile;
    }

    // http://1313027383.vod2.myqcloud.com/b5eaa628vodsh1313027383/0adc914b387702304670194180/f0.mp4
    // http://1313027383.vod2.myqcloud.com/b5eaa628vodsh1313027383/5529e3ec387702304671049037/f0.mp4
    public static String urlSignHandler(String str) {
        if (StringUtils.isBlank(str)) {
            return str;
        }
        if (!str.contains("http")) {
            return str;
        }
        String s1 = str.split("://")[1];
        String[] s2 = s1.split("/");
        String path = "/"+s2[1]+"/"+s2[2]+"/";
        // 2小时过期
        long ets = (System.currentTimeMillis() + 2L * 60L * 60L * 1000L) / 1000;
        // 时间戳十六进制
        String ets16 = String.format("%08x", ets);
        // key/链接path/时间戳
        String v = DigestUtils.md5Hex(Constant.key + path + ets16);
        return str+"?t="+ets16+"&sign="+v;
    }

    /**
     13      * 获取用户真实IP地址，不使用request.getRemoteAddr();的原因是有可能用户使用了代理软件方式避免真实IP地址,
     14 *
     16      * 可是，如果通过了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP值，究竟哪个才是真正的用户端的真实IP呢？
     17      * 答案是取X-Forwarded-For中第一个非unknown的有效IP字符串。
     18      *
     19      * 如：X-Forwarded-For：192.168.1.110, 192.168.1.120, 192.168.1.130,
     20      * 192.168.1.100
     21      *
     22      * 用户真实IP为： 192.168.1.110
     23      *
     24      * @param request
     25      * @return
     26      */
    public static String getIpAdd(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

}
