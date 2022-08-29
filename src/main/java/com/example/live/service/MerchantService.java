package com.example.live.service;

import com.alibaba.fastjson.JSONObject;
import com.example.live.common.BaseResult;
import com.example.live.entity.Invoice;

import javax.servlet.http.HttpSession;

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

    BaseResult<?> merchantSearch();

    BaseResult<?> merchantInfo(HttpSession session, String tag);

    BaseResult<?> merchantOrderList();

    BaseResult<?> merchantShopBind(JSONObject jo);

    BaseResult<?> merchantShop();

    BaseResult<?> merchantShopModify(JSONObject jo);

    BaseResult<?> merchantShopDel(String shopId);

    BaseResult<?> videoCentre(Integer type, Integer page);

    BaseResult<?> videoPlay(Integer id);

    BaseResult<?> merchantContractContent();

    BaseResult<?> merchantContractModify(JSONObject jo);

    BaseResult<?> merchantInvoiceCreate(Invoice invoice);

    BaseResult<?> paySuccessCheck(String orderNo);

    BaseResult<?> merchantSignCheck();

    BaseResult<?> merchantSignCreate(Integer type, Integer buyType);

    BaseResult<?> merchantSignUrl(String flowId);

    BaseResult<?> merchantSignStatus(String flowId);

}
