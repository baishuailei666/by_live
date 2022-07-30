package com.example.live.single;

import com.example.live.entity.Order;
import com.example.live.mapper.*;
import com.example.live.util.GeneralUtil;
import com.example.live.vo.MerchantVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

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
    private OrderMapper orderMapper;
    @Autowired
    private AnchorMapper anchorMapper;

    @Async("asyncThread")
    public void asyncAudit(MerchantVO mvo, String note) {
        // 提交审核
        merchantAuditMapper.merchantShopAudit(mvo.getId(), mvo.getOpeUser());
        // 消息通知
        contentMapper.insContent(mvo.getId(), mvo.getOpeUser(), note, 3);
        System.out.println("## asyncAudit");
    }

    @Async("asyncThread")
    public void asyncMerchantLogin(int merchantId, int loginCount) {
        // 更新登录时间、登录次数
        merchantMapper.updateLt(merchantId, loginCount + 1);
        System.out.println("## asyncMerchantLogin");
    }

    @Async("asyncThread")
    public void asyncMerchantOrder(int merchantId, int days) {
        // 更新会员信息
        Order order = orderMapper.getOrder1(merchantId);
        if (order != null) {
            int days2 = GeneralUtil.buyDays(order.getBuyType(), order.getUt());
            if (days2 < days && days2 > 0) {
                merchantMapper.updateDays(merchantId, days2);
            }
        }
        System.out.println("## asyncMerchantOrder");
    }

    @Async("asyncThread")
    public void asyncInsertMerchantAnchor(int merchantId, int anchorId) {
        int i = anchorMapper.queryAnchor(merchantId, anchorId);
        if (i <= 0) {
            anchorMapper.insertAnchor(merchantId, anchorId);
            System.out.println("## asyncInsertMerchantAnchor");
        }
    }

}
