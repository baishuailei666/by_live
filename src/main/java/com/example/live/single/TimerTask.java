package com.example.live.single;

import com.example.live.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


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

    @Value("${server.port}")
    private String port;

    // true-不执行、false-执行
    // back执行、merchant不执行
    private boolean execHandler() {
        System.out.println("port:"+port);
        return !port.equals("8081");
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
