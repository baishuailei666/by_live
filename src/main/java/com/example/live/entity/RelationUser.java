package com.example.live.entity;

import lombok.Data;

/**
 * 用户间的从属关系
 * （管理员-业务员）
 *
 * @author baishuailei@zhejianglab.com
 * @date 2022/7/8
 */
@Data
public class RelationUser {

    private int id;
    // 主user_id
    private int mainUserId;
    // 子user_id
    private int childUserId;
    private String ts;
}
