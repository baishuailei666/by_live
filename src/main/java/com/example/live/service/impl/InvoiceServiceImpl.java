package com.example.live.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.example.live.common.BaseResult;
import com.example.live.controller.query.InvoiceQuery;
import com.example.live.mapper.InvoiceMapper;
import com.example.live.service.InvoiceService;
import com.example.live.util.MailUtil;
import com.example.live.util.UserUtil;
import com.example.live.vo.InvoiceVo;
import com.example.live.vo.UserVO;
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

    @Override
    public BaseResult<?> invoiceList(JSONObject jo) {
        UserVO user = UserUtil.getUser();
        InvoiceQuery invoiceQuery = jo.toJavaObject(InvoiceQuery.class);
        if (user == null) {
            return new BaseResult<>(14, "登录已过期，请重新登录");
        }
        if (user.getLevel() == 1) {
            return new BaseResult<>(13, "您没有权限查看！");
        }
        int i = invoiceMapper.invoiceListCount(invoiceQuery, user.getId());
        if (i == 0) {
            return new BaseResult<>();
        }
        List<InvoiceVo> invoices = invoiceMapper.invoiceList(invoiceQuery, user.getId());
        return new BaseResult<>(i, invoices);
    }

    @Override
    public BaseResult<?> invoiceCheck(JSONObject jo) {
        Integer id = jo.getInteger("id");
        Integer status = jo.getInteger("status");
        String remark = jo.getString("remark");
        UserVO user = UserUtil.getUser();
        if (user == null) {
            return new BaseResult<>(14, "登录已过期，请重新登录");
        }
        if (user.getLevel() == 1) {
            return new BaseResult<>(13, "您没有权限查看！");
        }
        if (id == null || status == null) {
            return new BaseResult<>(12, "传参有误，请检查");
        }
        if (status == 1) {
            try {
                mailUtil.sendMailHandler(user.getId(), jo);
            } catch (Exception e) {
                return new BaseResult<>(14, "邮件发送失败，请重新提交！");
            }
        }
        invoiceMapper.invoiceUpdate(id, status, remark);
        return new BaseResult<>();
    }

}
