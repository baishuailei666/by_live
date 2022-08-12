package com.example.live.single;

import com.example.live.entity.RelationUser;
import com.example.live.entity.ResourceMerchant;
import com.example.live.mapper.*;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * 定时任务处理
 *
 * @author baishuailei@zhejianglab.com
 * @date 2022/7/10 17:29
 */
@Component
@EnableScheduling
public class TimerTask {

    @Autowired
    private AsyncService asyncService;
    @Autowired
    private MobileCodeMapper mobileCodeMapper;
    @Autowired
    private ResourceMerchantMapper resourceMerchantMapper;

    // true-不执行、false-执行
    private boolean execHandler() {
        return false;
    }

    // 每天23:30进行数据资源分配
    @Scheduled(cron = "0 30 23 * * ?")
//    @Scheduled(cron = "0/30 * * * * ?")
    public void resourceHandler() {
        if (execHandler()) {
            return;
        }
        System.out.println("## resourceHandler");
        asyncService.asyncResourceHandler();
    }

    // 每天23:50处理会员过期用户
//    @Scheduled(cron = "0 50 23 * * ?")
//    public void merchantExpireHandler() {
//
//    }


    // 每月最后一天的23:30分执行 28-31，考虑到了每月最短和最长的天数
    @Scheduled(cron = "0 30 23 28-31 * ?")
    public void clearHandler() {
        if (!execHandler()) {
            return;
        }
        System.out.println("## clearHandler");

        // 已失效的验证码
        mobileCodeMapper.clear();

        // 已处理的资源
        resourceMerchantMapper.needClearList();
    }

}
