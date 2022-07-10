package com.example.live.contorller;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.AlipayConstants;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.example.live.common.BaseResult;
import com.example.live.common.PayInfo;
import com.example.live.entity.PayConfig;
import com.example.live.mapper.PayConfigMapper;
import com.example.live.util.UserUtil;
import com.github.binarywang.wxpay.bean.notify.WxPayNotifyResponse;
import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyResult;
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderRequest;
import com.github.binarywang.wxpay.config.WxPayConfig;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.WxPayService;
import com.github.binarywang.wxpay.service.impl.WxPayServiceImpl;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;


/**
 * 支付购买
 * 支付宝、微信
 */
@Slf4j
@RestController
@RequestMapping("/pay")
public class PayController {

    @Resource
    private WxPayService wxService;
    @Autowired
    private PayConfigMapper payConfigMapper;


    /**
     * 支付宝
     */
    @RequestMapping("/ali")
    public void aliPay(Integer type, HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws IOException {
        Integer userId = UserUtil.getUserId();
        if (userId ==null) {
            httpResponse.setContentType("text/html;charset=" + PayInfo.charset);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("code", -1001);
            jsonObject.put("status", 200);
            jsonObject.put("msg", "用户暂未登录");
            httpResponse.getWriter().write(jsonObject.toJSONString());
            httpResponse.getWriter().flush();
            httpResponse.getWriter().close();
            return;
        }

        //获得初始化的AlipayClient 向支付宝发送支付请求
        AlipayClient alipayClient = new DefaultAlipayClient(PayInfo.gatewayUrl, PayInfo.app_id,
                PayInfo.merchant_private_key, AlipayConstants.FORMAT_JSON, PayInfo.charset, PayInfo.alipay_public_key, PayInfo.sign_type);

        // 请求
        String form = "";
        String body = null;
        String subject = null;
        String totalAmount = "1";
        String outTradeNo = "";
        String platform = "";
        String sessionId = httpRequest.getSession().getId();
        try {
            // 网页端
            AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();// 创建API对应的request
            // 同步通知url: 前端路径
            alipayRequest.setReturnUrl(PayInfo.return_url);
            // 异步通知url: 后台服务接口路径
            alipayRequest.setNotifyUrl(PayInfo.notify_url);
            // 填充业务参数
            alipayRequest.setBizContent(bizContentBuild(outTradeNo, totalAmount, subject, body,
                    sessionId, platform, type));
            // 调用SDK生成表单
            form = alipayClient.pageExecute(alipayRequest).getBody();

        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        httpResponse.setContentType("text/html;charset=" + PayInfo.charset);
        httpResponse.getWriter().write(form);// 直接将完整的表单html输出到页面
        httpResponse.getWriter().flush();
        httpResponse.getWriter().close();
    }

    /**
     * 支付宝异步回调接口
     */
    @RequestMapping("/notify/ali")
    public void aliNotifyPay(HttpServletRequest request, HttpServletResponse response) throws AlipayApiException, IOException {
        Map<String, String> paramsMap = new HashMap<>();
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String name = parameterNames.nextElement();
            paramsMap.put(name, request.getParameter(name));
            //乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
            //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
        }

        PrintWriter out = response.getWriter();
        boolean signVerified = AlipaySignature.rsaCheckV1(paramsMap, PayInfo.alipay_public_key, PayInfo.charset, PayInfo.sign_type); // 调用SDK验证签名

        String sessionId = request.getSession().getId();
        String trade_no = request.getParameter("trade_no"); // 支付宝交易号
        String order_no = request.getParameter("out_trade_no"); // 获取订单号
        String total_fee = request.getParameter("total_amount"); // 用户支付金额
        String buyer_email = request.getParameter("buyer_email"); // 买家支付宝账号
        String buyerLogonId =request.getParameter("buyer_logon_id");//买家支付宝账号
        String trade_status = request.getParameter("trade_status"); // 交易状态
        String passback_params = request.getParameter("passback_params"); // 交易状态


        if (signVerified) {
            if (trade_status.equals("TRADE_FINISHED") || trade_status.equals("TRADE_SUCCESS")) {

                System.out.println("trade_no:"+trade_no);

            }
            out.println("success");
        } else {
            out.println("fail");
        }
    }
    // 支付页面构造函数
    private String bizContentBuild(String outTradeNo, String totalAmount, String subject, String body, String sessionId,
                                   String platform, Integer months) {
        StringBuilder str = new StringBuilder();
        str.append("{");
        str.append("\"out_trade_no\"");
        str.append(":\"");
        str.append(outTradeNo);
        str.append("\",");
        str.append("\"product_code\":\"");
        if ("m".equals(platform)) {
            str.append("QUICK_WAP_PAY");
        } else if ("mobile".equals(platform)) {
            str.append("QUICK_MSECURITY_PAY");
        } else {
            str.append("FAST_INSTANT_TRADE_PAY");
        }
        str.append("\",");
        str.append("\"total_amount\"");
        str.append(":");
        str.append(totalAmount);
        str.append(",");
        str.append("\"subject\" : \"");
        str.append(subject);
        str.append("\",");
        str.append("\"body\" : \"");
        str.append(body);
        str.append("\",");
        str.append("\"timeout_express\" : \"");
        str.append("15m");
        str.append("\",");
        str.append("\"months\" : \"");
        str.append(months);
        str.append("\",");
        str.append("\"passback_params\" :");
        str.append("\"session_id%3d");
        str.append(sessionId);
        str.append("\" }");
        return str.toString();
    }


    /**
     * 微信
     */
    @RequestMapping("/wx")
    public <T> T wxPay(Integer type, HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws IOException, WxPayException {
        Integer userId = UserUtil.getUserId();
        if (userId ==null) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("code", -1001);
            jsonObject.put("status", 200);
            jsonObject.put("msg", "用户暂未登录");
            httpResponse.setContentType("text/html;charset=" + PayInfo.charset);
            httpResponse.getWriter().write(jsonObject.toJSONString());
            httpResponse.getWriter().flush();
            httpResponse.getWriter().close();
            return (T) new BaseResult<>(httpResponse);
        }

        // 4位随机数
        int random = (int)((Math.random()*9+1)*1000);
        String outTradeNo = "HT-" + type + "-"+ random;
        String totalAmount = "1";// 付款金额（必填）
        String subject = "灰豚数据(抖音版)";// 订单名称（必填）
        String body = null;// 商品描述 （可空）

        // 调用微信支付接口
        WxPayUnifiedOrderRequest request = new WxPayUnifiedOrderRequest();
        request.setProductId("1");
        request.setOutTradeNo(outTradeNo);
        request.setSpbillCreateIp("122.234.60.79");
        request.setBody(subject+"-"+body+"个月");
        request.setDetail(subject+"-"+body+"个月");
//        // 设置支付过期时间：30分钟
//        request.setTimeExpire(getOrderExpireTime(30*sec));
        // 微信支付的金额是不能带小数点的，乘以100提交，因为里面设置参数的时候是以"分"为单位的
        // 订单金额，单位为分
        request.setTotalFee((int) (NumberUtils.toFloat(totalAmount) * 100));
        request.setNotifyUrl(PayInfo.notify_url2);//线上回调地址
        request.setAttach(httpRequest.getSession().getId());//附加数据sessionId
        request.setTradeType("NATIVE"); //网页支付

        this.wxService.setConfig(wxPayConfig());
        Object codeUrl = this.wxService.createOrder(request);
        Map<String, Object> map = Maps.newHashMap();
        map.put("codeUrl", codeUrl);
        map.put("orderNo", outTradeNo);
        return (T) new BaseResult<>(map);
    }

    /**
     * 微信异步回调接口
     */
    @PostMapping("/notify/wx")
    public String wxNotifyPay(@RequestBody String xmlData) throws WxPayException {
        this.wxService.setConfig(wxPayConfig());

        final WxPayOrderNotifyResult notifyResult = this.wxService.parseOrderNotifyResult(xmlData);

        String trade_no = notifyResult.getTransactionId(); // 交易号
        String order_no = notifyResult.getOutTradeNo(); // 获取订单号
        long total_fee = (long) notifyResult.getTotalFee() / 100; // 用户支付金额
        String attach = notifyResult.getAttach(); // 交易状态

        return WxPayNotifyResponse.success("支付成功");
    }

    @Bean
    public WxPayService wxPayService() {
        return new WxPayServiceImpl();
    }

    // 自定义构造支付参数
    public WxPayConfig wxPayConfig() {
        Integer loginUserId = UserUtil.getUserId();

        // loginUserId -> agency_user_id
        PayConfig payConfig = payConfigMapper.getConfig(loginUserId);
        if (payConfig==null) {
            // 支付参数无效
            return null;
        }
        WxPayConfig config = new WxPayConfig();
        config.setAppId(StringUtils.trimToNull(payConfig.getWxAppId()));
        config.setMchId(StringUtils.trimToNull(payConfig.getWxMchId()));
        config.setMchKey(StringUtils.trimToNull(payConfig.getWxMchKey()));
        // 支付证书（考虑下如何存储和读取）
        config.setKeyPath(StringUtils.trimToNull(payConfig.getWxKeyPath()));
        // 可以指定是否使用沙箱环境
        config.setUseSandboxEnv(false);
        return config;
    }


}
