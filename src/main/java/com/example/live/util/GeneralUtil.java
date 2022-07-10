package com.example.live.util;

import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
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

    // 自定义:page=1、size=10
    public static int indexPage(int page, int size) {
        page = page ==0 ? 1 : page;
        size = size ==0 ? 10 : size;
        return (page -1) * size;
    }

    // 总页码
    public static int pages(int count, int size) {
        return (count + size -1) / size;
    }

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
}
