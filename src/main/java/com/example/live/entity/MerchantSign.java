package com.example.live.entity;

import lombok.Data;

/**
 * 用于电子签信息
 *
 * @author baishuailei@zhejianglab.com
 * @date 2022/7/26 13:52
 */
@Data
public class MerchantSign {
    private int id;
    private int merchantId;
    // 小店主体名称
    private String subject;
    // 签署人
    private String person;
    // 手机号码
    private String mobile;
    // 统一社会信用代码/身份证号码
    private String tax;

    private String shop;
    private String shopId;

}
