package com.example.live.vo;

import com.example.live.entity.LevelRight;
import lombok.Data;

import java.util.List;

/**
 * 商户端系统：
 * 个人中心
 *
 * @author baishuailei@zhejianglab.com
 * @date 2022/7/10 14:23
 */
@Data
public class MerchantVO {
    private int id;
    private String mobile;
    private String shopId;
    private String shop;
    private Integer opeUser;
    // 对应业务员微信
    private String opeUserWx;
    private String buyType;
    private int vipType;
    // 剩余天数
    private Integer days;
    private String ct;
    private String lt;

}
