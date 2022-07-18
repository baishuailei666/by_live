package com.example.live.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.example.live.common.BaseEnum;
import com.example.live.common.BaseResult;
import com.example.live.common.Constant;
import com.example.live.controller.query.InvoiceQuery;
import com.example.live.mapper.InvoiceMapper;
import com.example.live.service.CommonService;
import com.example.live.service.InvoiceService;
import com.example.live.util.GeneralUtil;
import com.example.live.util.MailUtil;
import com.example.live.util.UserUtil;
import com.example.live.vo.InvoiceVO;
import com.example.live.vo.UserVO;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author baishuailei@zhejianglab.com
 * @date 2022/7/10 13:47
 */
@Service("invoiceService")
public class InvoiceServiceImpl implements InvoiceService {


    @Autowired
    private InvoiceMapper invoiceMapper;
    @Autowired
    private MailUtil mailUtil;
    @Autowired
    private CommonService commonService;

    @Override
    public BaseResult<?> invoiceList(JSONObject jo) {
        UserVO user = UserUtil.getUser();
        InvoiceQuery invoiceQuery = jo.toJavaObject(InvoiceQuery.class);
        if (user == null) {
            return new BaseResult<>(BaseEnum.No_Login);
        }
        List<Integer> opeUserIds = Lists.newArrayList();
        if (user.getLevel()!=3) {
            // 不是业务员级别
            opeUserIds = commonService.opeUserIds(user.getId());
        } else {
            opeUserIds.add(user.getId());
        }
        invoiceQuery.setIds(opeUserIds);
        invoiceQuery.setPage(GeneralUtil.indexPage(invoiceQuery.getPage()));

        int i = invoiceMapper.invoiceListCount(invoiceQuery);
        if (i == 0) {
            return new BaseResult<>();
        }
        List<InvoiceVO> invoices = invoiceMapper.invoiceList(invoiceQuery);
        invoices.forEach(vo -> vo.setStatus(Constant.invoiceStatusMap.get(Integer.valueOf(vo.getStatus()))));
        return new BaseResult<>(i, invoices);
    }

    @Override
    public BaseResult<?> invoiceCheck(JSONObject jo) {
        Integer id = jo.getInteger("id");
        Integer status = jo.getInteger("status");
        String remark = jo.getString("remark");
        UserVO user = UserUtil.getUser();
        if (user == null) {
            return new BaseResult<>(BaseEnum.No_Login);
        }
        if (user.getLevel() == 3) {
            return new BaseResult<>(14, "暂无操作权限");
        }
        if (id == null || status == null) {
            return new BaseResult<>(11, "参数不能为空");
        }
        // status：1-通过、2-拒绝
        if (status!=1&&status!=2) {
            return new BaseResult<>(12, "参数错误");
        }
        // 审核通过发送邮箱
        if (status==1) {
            mailUtil.sendMailHandler(user.getAgentUser(), id);
        }
        invoiceMapper.invoiceUpdate(id, status, remark);
        return new BaseResult<>();
    }

}
