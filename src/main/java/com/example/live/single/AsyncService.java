package com.example.live.single;

import com.example.live.entity.RelationUser;
import com.example.live.entity.ResourceMerchant;
import com.example.live.mapper.*;
import com.example.live.util.GeneralUtil;
import com.example.live.vo.MerchantVO;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 异步方法处理
 *
 * @author baishuailei@zhejianglab.com
 * @date 2022/7/29 21:05
 */
@Component
public class AsyncService {

    @Autowired
    private MerchantAuditMapper merchantAuditMapper;
    @Autowired
    private ContentMapper contentMapper;
    @Autowired
    private MerchantMapper merchantMapper;
    @Autowired
    private AnchorMapper anchorMapper;
    @Autowired
    private ContractMapper contractMapper;
    @Autowired
    private ResourceMerchantMapper resourceMerchantMapper;
    @Autowired
    private RelationUserMapper relationUserMapper;
    @Autowired
    private UserMapper userMapper;



    @Async("asyncThread")
    public void asyncAudit(MerchantVO mvo, String note) {
        // 提交审核
        merchantAuditMapper.merchantShopAudit(mvo.getId(), mvo.getOpeUser());
        // 消息通知
        contentMapper.insContent(mvo.getId(), mvo.getOpeUser(), note, 3);
        System.out.println("## asyncAudit");
    }

    @Async("asyncThread")
    public void asyncMerchantLoginOrder(int merchantId, int loginCount, int days, boolean isVip) {
        // 更新登录时间、登录次数
        merchantMapper.updateLt(merchantId, loginCount + 1, days);
        System.out.println("## asyncMerchantLogin");
    }

    @Async("asyncThread")
    public void asyncInsertMerchantAnchor(int merchantId, int anchorId) {
        int i = anchorMapper.queryAnchor(merchantId, anchorId);
        if (i <= 0) {
            anchorMapper.insertAnchor(merchantId, anchorId);
            System.out.println("## asyncInsertMerchantAnchor");
        }
    }

    @Async("asyncThread")
    public void asyncSignStatusHandler(List<Integer> ids) {
        contractMapper.updateStatus1(ids);
        System.out.println("## asyncSignStatusHandler");
    }

    public void asyncResourceHandler() {
        // 资源池
        List<ResourceMerchant> data = resourceMerchantMapper.taskResource();
        if (data.size()!=0) {
            // 管理员以上用户 level<=2
            List<Integer> ids = userMapper.level2User();
            if (ids.size()!=0) {
                List<RelationUser> list = relationUserMapper.relationUserList(ids);
                // 管理员所属的业务员
                Map<Integer, List<RelationUser>> agentMap = list.stream().collect(Collectors.groupingBy(RelationUser::getMainUserId));
                // 管理员所属商户资源
                Map<Integer, List<ResourceMerchant>> agentResourceMap = data.stream().collect(Collectors.groupingBy(ResourceMerchant::getAgentUser));
                ids.forEach(agent ->{
                    // 业务员
                    List<RelationUser> list1 = agentMap.getOrDefault(agent, null);
                    // 资源数据
                    List<ResourceMerchant> list2 = agentResourceMap.getOrDefault(agent, null);

                    if (list1!=null&&list1.size()!=0 && list2!=null&&list2.size()!=0) {
                        // 计算商户资源、业务员的分配系数
                        int coe = list2.size() / list1.size();
                        if (coe<5) {
                            list2.forEach(rm -> rm.setOpeUser(list1.get(0).getChildUserId()));
                            resourceMerchantMapper.taskDistribution(list2);
                            System.out.println("# asyncResourceHandler:数据过少默认分配给第一个业务员");
                        } else {
                            // 方法一：随机分
                            list2.forEach(rm -> {
                                int i = GeneralUtil.getIntRandom(list1.size());
                                rm.setOpeUser(list1.get(i).getChildUserId());
                            });
                            resourceMerchantMapper.taskDistribution(list2);


//                              // 方法二：数组索引分+随机分
//                            List<ResourceMerchant> list4 = Lists.newArrayList();
//                            for (int i=0; i<list1.size(); i++) {
//                                int s = i*coe;
//                                int e = (i+1)*coe;
//                                if (e>=list2.size()) {
//                                    e = list2.size();
//                                }
//
//                                List<ResourceMerchant> list3 = list2.subList(s, e);
//
//                                RelationUser ru = list1.get(i);
//                                list3.forEach(rm -> rm.setOpeUser(ru.getChildUserId()));
//                                list4.addAll(list3);
//                                // 需要添加 &allowMultiQueries=true
//                                resourceMerchantMapper.taskDistribution(list3);
//                            }
//
//                            // 未分配的资源重新进行分配
//                            list2.retainAll(list4);
//                            // 避免出现未分配的资源
//                            if (list2.size()!=0) {
//                                list2.forEach(rm -> {
//                                    int i = GeneralUtil.getIntRandom(list1.size());
//                                    rm.setOpeUser(list1.get(i).getChildUserId());
//                                });
//                                resourceMerchantMapper.taskDistribution(list2);
//                            }
                        }
                    }

                });
            }
        }
        System.out.println("ok");
    }

}
