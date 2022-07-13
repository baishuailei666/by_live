package com.example.live.service;

import com.alibaba.fastjson.JSONObject;
import com.example.live.common.BaseResult;

/**
 * @author baishuailei@zhejianglab.com
 * @date 2022/7/10 13:47
 */
public interface InvoiceService {

    /**
     * 发票列表，可筛选
     * @param jo
     * @return
     */
    BaseResult<?> invoiceList(JSONObject jo);

    /**
     * 是否给开发票
     * @param jo
     * @return
     */
    BaseResult<?> invoiceUpdate(JSONObject jo);
}
