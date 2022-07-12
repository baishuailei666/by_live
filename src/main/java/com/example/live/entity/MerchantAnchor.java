package com.example.live.entity;

import lombok.Data;

/**
 * 商户主播已查看
 *
 * @author baishuailei@zhejianglab.com
 * @date 2022/7/8 22:29
 */
@Data
public class MerchantAnchor {
    private int id;
    // 商户id
    private int merchantId;
    // 主播id
    private int anchorId;
    private String ts;
    // 已加微信-1
    private int add;
}
