package com.example.live.service;

import com.alibaba.fastjson.JSONObject;
import com.example.live.common.BaseResult;
import com.example.live.entity.Contract;
import com.example.live.entity.Invoice;

/**
 * @author baishuailei@zhejianglab.com
 * @date 2022/7/10 13:42
 */
public interface MerchantService {

    /**
     * 查询商户列表，包含手机、店铺名、状态条件筛选
     * @param jo
     * @return
     */
    BaseResult<?> getMerchantListByParams(JSONObject jo);

    BaseResult<?> merchantOrderList();

    BaseResult<?> merchantShopBind(JSONObject jo);

    BaseResult<?> merchantShop();

    BaseResult<?> merchantShopModify(JSONObject jo);

    BaseResult<?> merchantShopDel(String shopId);

    BaseResult<?> videoCentre(Integer type);

    BaseResult<?> videoPlay(Integer id);

    BaseResult<?> merchantContractCreate(Contract contract);

    BaseResult<?> merchantContractModify(Contract contract);

    BaseResult<?> merchantInvoiceCreate(Invoice invoice);

    BaseResult<?> paySuccessCheck(String orderNo);

    BaseResult<?> merchantSignCreate(Integer type);

    BaseResult<?> merchantSignAgree(String flowId);

    BaseResult<?> merchantSignUrl(String flowId);

}
