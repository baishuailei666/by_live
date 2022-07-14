package com.example.live.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.live.common.BaseResult;
import com.example.live.service.impl.InvoiceServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author Chen Rui
 * @Date 2022/7/13 19:40
 * @Description 发票管理
 */
@RestController
@RequestMapping("/invoice")
public class InvoiceController {

    @Autowired
    private InvoiceServiceImpl invoiceService;

    /**
     * 发票列表，可筛选
     * @param jo
     * @return
     */
    @PostMapping("/list")
    public BaseResult<?> invoiceList(JSONObject jo) {
        return invoiceService.invoiceList(jo);
    }


    /**
     * 发票审核 id:发票id status:-1通过，2拒绝 remark:备注，拒绝原因
     * @param jo
     * @return
     */
    @PostMapping("/check")
    public BaseResult<?> invoiceCheck(JSONObject jo){
        return invoiceService.invoiceCheck(jo);
    }



}
