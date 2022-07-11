package com.example.live.entity;

import lombok.Data;

/**
 * 备注
 * （某条数据多个备注、跟进记录
 *
 * @author baishuailei@zhejianglab.com
 * @date 2022/7/8 21:40
 */
@Data
public class Content {
    private int id;
    // 主对象id
    private int oid;
    // 接收对象id
    private int rid;
    // 内容
    private String note;
    // 跟进记录-1、备注-2、消息通知-3
    private int type;
    // 时间
    private String ts;
}
