package com.example.live.single;

import com.example.live.mapper.*;
import com.example.live.vo.MerchantVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.List;

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


    @Async("asyncThread")
    public void asyncAudit(MerchantVO mvo, String note) {
        // 提交审核
        merchantAuditMapper.merchantShopAudit(mvo.getId(), mvo.getOpeUser());
        // 消息通知
        contentMapper.insContent(mvo.getId(), mvo.getOpeUser(), note, 3);
        System.out.println("## asyncAudit");
    }

    @Async("asyncThread")
    public void asyncMerchantLoginOrder(int merchantId, int loginCount, int days, int curDays) {
        // 更新登录时间、登录次数
        int val = Math.min(days, curDays);
        merchantMapper.updateLt(merchantId, loginCount+1, val);
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

}
