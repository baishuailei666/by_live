package com.example.live.common;

/**
 * @author baishuailei@zhejianglab.com
 * @date 2022/7/18 9:44
 */
public enum BaseEnum {

    No_Login(10, "登录已过期,请重新登录"),

    SUCCESS(0, "操作成功");

    int code;
    String msg;

    BaseEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
