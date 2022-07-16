package com.example.live.vo;

import lombok.Data;

/**
 * @author baishuailei@zhejianglab.com
 * @date 2022/7/13 14:53
 */
@Data
public class ResourceMerchantVO {
    private int id;
    private String shop;
    // 手机号
    private String mobile;
    // 管理员（根据当前上传的user)
    private Integer agentUser;
    // 意向程度：未联系-0、跟进中-1、已处理-2、已拒绝-3
    private String intention;
    // 业务员
    private Integer opeUser;
    // 最新一条备注
    private String content;
    // 总备注条数
    private Integer contentTotal;
    // 创建时间
    private String ct;
    // 更新时间
    private String ut;
}
