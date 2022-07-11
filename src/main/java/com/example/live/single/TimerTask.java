package com.example.live.single;

import com.example.live.entity.RelationUser;
import com.example.live.entity.ResourceMerchant;
import com.example.live.mapper.MobileCodeMapper;
import com.example.live.mapper.RelationUserMapper;
import com.example.live.mapper.ResourceMerchantMapper;
import com.example.live.mapper.UserMapper;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
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
    private MobileCodeMapper mobileCodeMapper;
    @Autowired
    private ResourceMerchantMapper resourceMerchantMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RelationUserMapper relationUserMapper;

    // 每天23:30进行数据资源分配
    @Scheduled(cron = "0 30 23 * * ?")
//    @Scheduled(cron = "0/30 * * * * ?")
    public void resourceHandler() {
        // 资源池
        List<ResourceMerchant> data = resourceMerchantMapper.taskResource();
        if (data.size()!=0) {
            // 管理员以上用户
            List<Integer> ids = userMapper.levelUser();
            if (ids.size()!=0) {
                List<RelationUser> list = relationUserMapper.relationUserList(ids);
                // 管理员所属的业务员
                Map<Integer, List<RelationUser>> agentMap = list.stream().collect(Collectors.groupingBy(RelationUser::getMainUserId));
                // 管理员所属商户资源
                Map<Integer, List<ResourceMerchant>> agentResourceMap = data.stream().collect(Collectors.groupingBy(ResourceMerchant::getAgentUser));
                ids.forEach(agent ->{
                    List<RelationUser> list1 = agentMap.getOrDefault(agent, null);
                    if (list1==null || list1.size()==0) {
                        // 发送邮件
                        System.out.println("#管理员所属的业务员为空,agent:"+agent);
                        return;
                    }
                    List<ResourceMerchant> list2 = agentResourceMap.getOrDefault(agent, null);
                    if (list2==null || list2.size()==0) {
                        // 发送邮件
                        System.out.println("#管理员所属商户资源为空,agent:"+agent);
                        return;
                    }
                    // 计算商户资源、业务员的分配系数
                    int coe = list2.size() / list1.size();
                    if (coe<5) {
                        System.out.println("#数据过少不参与计算分配");
                        return;
                    }
                    List<ResourceMerchant> list4 = Lists.newArrayList();
                    for (int i=0; i<list1.size(); i++) {
                        int s = i*coe;
                        int e = (i+1)*coe;
                        if (e>=list2.size()) {
                            e = list2.size();
                        }

                        List<ResourceMerchant> list3 = list2.subList(s, e);

                        RelationUser ru = list1.get(i);
                        list3.forEach(rm -> rm.setOpeUser(ru.getChildUserId()));
                        // 需要添加 &allowMultiQueries=true
                        resourceMerchantMapper.taskDistribution(list3);

                        list3.forEach(r ->{
                            if (r.getOpeUser()==null) {
                                list4.add(r);
                            }
                        });
                    }
                    // 避免出现未分配的资源
                    if (list4.size()!=0) {
                        resourceMerchantMapper.taskDistribution(list4);
                    }
                });
            }
        }

    }


    // 每月最后一天的23:30分执行 28-31，考虑到了每月最短和最长的天数
    @Scheduled(cron = "0 30 23 28-31 * ?")
    public void clearMobileCode() {
        // 已失效的验证码
        mobileCodeMapper.clear();

        // 已处理的资源
        resourceMerchantMapper.needClearList();
    }

}
