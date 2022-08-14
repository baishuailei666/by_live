package com.example.live.entity;

import lombok.Data;

/**
 * 视频
 *
 * @author baishuailei@zhejianglab.com
 * @date 2022/7/10 10:07
 */
@Data
public class Video {
    private int id;
    // 视频标题
    private String title;
    // 视频地址
    private String path;
    // 全部-0、月卡-1、季卡-2、年卡-3
    private int level;
    // 视频封面
    private String cover;
    private String ct;
}
