package com.example.live.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.live.common.BaseResult;
import com.example.live.entity.Contract;
import com.example.live.entity.Invoice;
import com.example.live.service.impl.MerchantAuditServiceImpl;
import com.example.live.service.impl.MerchantServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author Chen Rui
 * @Date 2022/7/10 16:14
 * @Description 商户管理
 */
@RestController
@RequestMapping("/merchant")
public class MerchantController {

    @Autowired
    private MerchantServiceImpl merchantService;

    @Autowired
    private MerchantAuditServiceImpl merchantAuditService;

    /**
     * 商户订单记录
     *
     * @return
     */
    @GetMapping("/order")
    public BaseResult<?> merchantOrder() {
        return merchantService.merchantOrderList();
    }


    /**
     * 商户绑定店铺
     *
     * @param jo 店铺id：shopId、店铺名称：shop、商品链接：goods、商家介绍：introduce
     * @return
     */
    @PostMapping("/shop/bind")
    public BaseResult<?> merchantShopBind(@RequestBody JSONObject jo) {
        return merchantService.merchantShopBind(jo);
    }

    /**
     * 我的店铺
     *
     * @return
     */
    @GetMapping("/shop")
    public BaseResult<?> merchantShop() {
        return merchantService.merchantShop();
    }

    /**
     * 商户修改店铺
     *
     * @param jo 店铺id：shopId、店铺名称：shop、商品链接：goods、商家介绍：introduce
     * @return
     */
    @PostMapping("/shop/modify")
    public BaseResult<?> merchantShopModify(@RequestBody JSONObject jo) {
        return merchantService.merchantShopModify(jo);
    }

    /**
     * 删除店铺
     *
     * @param shopId 店铺ID
     * @return
     */
    @GetMapping("/shop/del")
    public BaseResult<?> merchantShopDel(@RequestParam("shopId") String shopId) {
        return merchantService.merchantShopDel(shopId);
    }

    /**
     * 视频中心
     *
     * @param type 1-月卡、2-季卡、3-年卡
     * @return
     */
    @GetMapping("/video/centre")
    public BaseResult<?> videoCentre(@RequestParam("type") Integer type) {
        return merchantService.videoCentre(type);
    }

    /**
     * 视频播放
     *
     * @param id
     * @return
     */
    @GetMapping("/video/play")
    public BaseResult<?> videoPlay(@RequestParam("id") Integer id) {
        return merchantService.videoPlay(id);
    }

    /**
     * 提交合同
     *
     * @param contract
     * @return
     */
    @PostMapping("/contract/create")
    public BaseResult<?> merchantContractCreate(@RequestBody Contract contract) {
        return merchantService.merchantContractCreate(contract);
    }

    /**
     * 修改合同
     *
     * @param contract
     * @return
     */
    @PostMapping("/contract/modify")
    public BaseResult<?> merchantContractModify(@RequestBody Contract contract) {
        return merchantService.merchantContractModify(contract);
    }

    /**
     * 提交发票
     *
     * @param invoice
     * @return
     */
    @PostMapping("/invoice/create")
    public BaseResult<?> merchantInvoiceCreate(@RequestBody Invoice invoice) {
        return merchantService.merchantInvoiceCreate(invoice);
    }

    /**
     * 微信支付验收是否支付成功
     *
     * @param orderNo 订单号
     * @return
     */
    @GetMapping("/paySuccess")
    public BaseResult<?> paySuccessCheck(@RequestParam("orderNo") String orderNo) {
        return merchantService.paySuccessCheck(orderNo);
    }


    /**
     * 商户电子签
     *
     * @param type 0-企业、1-个人
     * @return
     */
    @GetMapping("/sign/create")
    public BaseResult<?> merchantSignCreate(@RequestParam("type") Integer type) {
        return merchantService.merchantSignCreate(type);
    }

    /**
     * 商户电子签-同意
     * @param flowId
     * @return
     */
    @GetMapping("/sign/agree")
    public BaseResult<?> merchantSignAgree(@RequestParam("flowId") String flowId) {
        return merchantService.merchantSignAgree(flowId);
    }

    /**
     * 商户电子签-获取链接
     *
     * @param flowId
     * @return
     */
    @GetMapping("/sign/url")
    public BaseResult<?> merchantSignUrl(@RequestParam("flowId") String flowId) {
        return merchantService.merchantSignUrl(flowId);
    }

}
