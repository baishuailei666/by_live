package com.example.live.controller;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.AlipayConstants;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.example.live.common.BaseResult;
import com.example.live.common.Constant;
import com.example.live.entity.Order;
import com.example.live.entity.PayConfig;
import com.example.live.mapper.DataConfigMapper;
import com.example.live.mapper.OrderMapper;
import com.example.live.mapper.PayConfigMapper;
import com.example.live.service.CommonService;
import com.example.live.util.DateUtil;
import com.example.live.util.GeneralUtil;
import com.example.live.util.UserUtil;
import com.example.live.vo.MerchantVO;
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
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;

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
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private CommonService commonService;
    @Autowired
    private DataConfigMapper dataConfigMapper;


    // 获取代理商支付配置信息
    public PayConfig getPayConfig(Integer agentUser) {
        PayConfig config = payConfigMapper.getConfig(agentUser);
        if (config==null) {
            config = payConfigMapper.getConfig(Constant.admin_id);
        }
        return config;
    }

    /**
     * 支付宝
     */
    @RequestMapping("/ali")
    public void aliPay(Integer type, HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws IOException {
        MerchantVO mvo = UserUtil.getMerchant();
        if (mvo ==null) {
            httpResponse.setContentType("text/html;charset=" + Constant.charset);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("code", 11);
            jsonObject.put("status", 200);
            jsonObject.put("data", null);
            jsonObject.put("msg", "用户登录状态已过期");
            httpResponse.getWriter().write(jsonObject.toJSONString());
            httpResponse.getWriter().flush();
            httpResponse.getWriter().close();
            return;
        }

        // 请求
        String form = "";
        String body = null;
        String totalAmount;
        String platform = "";
        String subject = Constant.buy_subject;
        String outTradeNo = GeneralUtil.getOrderNo(type);
        String sessionId = httpRequest.getSession().getId();

        Integer agentUser = commonService.merchantAgentUser(mvo.getOpeUser());
        String data = dataConfigMapper.getConfigStr(agentUser);
        String[] prices = GeneralUtil.getAgentConfig(data, 2);
        if (type==1) {
            totalAmount = prices[0];
        } else if (type==2) {
            totalAmount = prices[1];
        } else if (type==3) {
            totalAmount = prices[2];
        } else {
            // 无效的请求参数
            httpResponse.setContentType("text/html;charset=" + Constant.charset);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("code", 10);
            jsonObject.put("data", null);
            jsonObject.put("status", 200);
            jsonObject.put("msg", "无效的请求参数");
            httpResponse.getWriter().write(jsonObject.toJSONString());
            httpResponse.getWriter().flush();
            httpResponse.getWriter().close();
            return;
        }

        Order order = new Order();
        order.setPayType(1);
        order.setBuyType(type);
        order.setOrderNo(outTradeNo);
        order.setMerchantId(mvo.getId());
        order.setOpeUser(mvo.getOpeUser());
        order.setMoney(Double.valueOf(totalAmount));
        orderMapper.insOrder(order);
        System.out.println("#pay ali:"+order);

        PayConfig payConfig = getPayConfig(agentUser);

        //获得初始化的AlipayClient 向支付宝发送支付请求
        AlipayClient alipayClient = new DefaultAlipayClient(Constant.gatewayUrl, payConfig.getAliAppId(),
                payConfig.getAliPrivateKey(), AlipayConstants.FORMAT_JSON, Constant.charset, payConfig.getAliPublicKey(), Constant.sign_type);
        try {
            // 网页端
            // 创建API对应的request
            AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
            // 同步通知url: 前端路径
            alipayRequest.setReturnUrl(Constant.return_url);
            // 异步通知url: 后台服务接口路径
            alipayRequest.setNotifyUrl(Constant.notify_url);
            // 填充业务参数
            alipayRequest.setBizContent(bizContentBuild(outTradeNo, totalAmount, subject, body,
                    sessionId, platform, type));
            // 调用SDK生成表单
            form = alipayClient.pageExecute(alipayRequest).getBody();

        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        httpResponse.setContentType("text/html;charset=" + Constant.charset);
        // 直接将完整的表单html输出到页面
        httpResponse.getWriter().write(form);
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

        // TODO 支付宝回调
        PrintWriter out = response.getWriter();
        boolean signVerified = AlipaySignature.rsaCheckV1(paramsMap, Constant.alipay_public_key, Constant.charset, Constant.sign_type); // 调用SDK验证签名

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
                Order order = orderMapper.getOrderByNo(order_no);
                if (StringUtils.isNotBlank(order.getTradeNo())) {
                    out.println("success");
                }
                order.setTradeNo(trade_no);
                order.setUt(DateUtil.getTime());
                order.setStatus(Constant.pay_success);
                orderMapper.updateSuccess(order);

                // todo 修改merchant shop_status、days
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
        MerchantVO mvo = UserUtil.getMerchant();
        if (mvo ==null) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("code", 11);
            jsonObject.put("data", null);
            jsonObject.put("status", 200);
            jsonObject.put("msg", "用户登录状态已过期");
            httpResponse.setContentType("text/html;charset=" + Constant.charset);
            httpResponse.getWriter().write(jsonObject.toJSONString());
            httpResponse.getWriter().flush();
            httpResponse.getWriter().close();
            return (T) new BaseResult<>(httpResponse);
        }

        String totalAmount;
        String body = Constant.buy_subject;
        String subject = Constant.buy_subject;
        String outTradeNo = GeneralUtil.getOrderNo(type);
        String sessionId = httpRequest.getSession().getId();

        Integer agentUser = commonService.merchantAgentUser(mvo.getOpeUser());
        String data = dataConfigMapper.getConfigStr(agentUser);
        String[] prices = GeneralUtil.getAgentConfig(data, 2);
        if (type==1) {
            totalAmount = prices[0];
            subject = subject+"-月卡";
        } else if (type==2) {
            totalAmount = prices[1];
            subject = subject+"-季卡";
        } else if (type==3) {
            totalAmount = prices[2];
            subject = subject+"-年卡";
        } else {
            // 无效的请求参数
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("code", 10);
            jsonObject.put("data", null);
            jsonObject.put("status", 200);
            jsonObject.put("msg", "无效的请求参数");
            httpResponse.setContentType("text/html;charset=" + Constant.charset);
            httpResponse.getWriter().write(jsonObject.toJSONString());
            httpResponse.getWriter().flush();
            httpResponse.getWriter().close();
            return (T) new BaseResult<>(httpResponse);
        }

        Order order = new Order();
        order.setPayType(2);
        order.setBuyType(type);
        order.setOrderNo(outTradeNo);
        order.setMerchantId(mvo.getId());
        order.setOpeUser(mvo.getOpeUser());
        order.setMoney(Double.valueOf(totalAmount));
        orderMapper.insOrder(order);
        System.out.println("#wx ali:"+order);


        // 调用微信支付接口
        WxPayUnifiedOrderRequest request = new WxPayUnifiedOrderRequest();
        request.setBody(body);
        request.setDetail(subject);
        request.setProductId("1");
        request.setOutTradeNo(outTradeNo);
        request.setSpbillCreateIp("122.234.60.79");
//        // 设置支付过期时间：30分钟
//        request.setTimeExpire(getOrderExpireTime(30*sec));
        // 微信支付的金额是不能带小数点的，乘以100提交，因为里面设置参数的时候是以"分"为单位的
        // 订单金额，单位为分
        request.setTotalFee((int) (NumberUtils.toFloat(totalAmount) * 100));
        request.setNotifyUrl(Constant.notify_url2);//线上回调地址
        request.setAttach(sessionId);//附加数据sessionId
        request.setTradeType("NATIVE"); //网页支付

        this.wxService.setConfig(wxPayConfig(agentUser));
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
        // TODO 微信支付回调
        this.wxService.setConfig(wxPayConfig(null));

        final WxPayOrderNotifyResult notifyResult = this.wxService.parseOrderNotifyResult(xmlData);

        String trade_no = notifyResult.getTransactionId(); // 交易号
        String order_no = notifyResult.getOutTradeNo(); // 获取订单号
        long total_fee = (long) notifyResult.getTotalFee() / 100; // 用户支付金额
        String attach = notifyResult.getAttach(); // 交易状态

        Order order = orderMapper.getOrderByNo(order_no);
        if (StringUtils.isNotBlank(order.getTradeNo())) {
            return WxPayNotifyResponse.success("支付成功");
        }
        order.setTradeNo(trade_no);
        order.setUt(DateUtil.getTime());
        order.setStatus(Constant.pay_success);
        orderMapper.updateSuccess(order);
        return WxPayNotifyResponse.success("支付成功");
    }

    @Bean
    public WxPayService wxPayService() {
        return new WxPayServiceImpl();
    }

    // 自定义构造支付参数
    public WxPayConfig wxPayConfig(Integer agentUser) {
        PayConfig payConfig = getPayConfig(agentUser);
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
