package com.example.live.controller;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.AlipayConstants;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.example.live.common.BaseEnum;
import com.example.live.common.BaseResult;
import com.example.live.common.Constant;
import com.example.live.entity.Merchant;
import com.example.live.entity.Order;
import com.example.live.mapper.DataConfigMapper;
import com.example.live.mapper.MerchantMapper;
import com.example.live.mapper.OrderMapper;
import com.example.live.util.DateUtil;
import com.example.live.util.GeneralUtil;
import com.example.live.util.UserUtil;
import com.example.live.vo.MerchantVO;
import com.github.binarywang.wxpay.bean.notify.WxPayNotifyResponse;
import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyResult;
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderRequest;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.WxPayService;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;


/**
 * 支付购买
 *      支付宝、微信
 */
@Slf4j
@RestController
@RequestMapping("/pay")
public class PayController {

    @Resource
    private WxPayService wxService;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private DataConfigMapper dataConfigMapper;
    @Autowired
    private MerchantMapper merchantMapper;


    // 更新merchant的shop_status、days
    public void successHandler(Order order) {
        int mid = order.getMerchantId();
        Merchant merchant = merchantMapper.getMerchant3(mid);
        if (merchant!=null) {
            String buy = "已认证-"+Constant.buyTypeMap.get(order.getBuyType());
            int days = GeneralUtil.typeDays(order.getBuyType());
            merchantMapper.updateMerchantDays(mid, buy, GeneralUtil.parseInt(merchant.getDays())+days);
        }
    }

    /**
     * 支付宝
     */
    @RequestMapping("/ali")
    public void aliPay(Integer type, String flowId, HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws IOException {
        MerchantVO mvo = UserUtil.getMerchant();
        if (mvo ==null) {
            httpResponse.setContentType("text/html;charset=" + Constant.charset);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("data", "null2");
            jsonObject.put("status", 200);
            jsonObject.put("msg", BaseEnum.No_Login.getMsg());
            jsonObject.put("code", BaseEnum.No_Login.getCode());
            httpResponse.getWriter().write(jsonObject.toJSONString());
            httpResponse.getWriter().flush();
            httpResponse.getWriter().close();
            return;
        }

        // 请求
        String body;
        String form = "";
        String totalAmount;
        String platform = "";
        String subject = Constant.buy_subject;
        String outTradeNo = GeneralUtil.getOrderNo(type)+"A";
        String sessionId = httpRequest.getSession().getId();

        String data = dataConfigMapper.getConfigStr(Constant.admin_id);
        String[] prices = GeneralUtil.getAgentConfig(data, 2);
        if (type==1) {
            totalAmount = prices[0];
            body = Constant.buyTypeMap.get(1);
        } else if (type==2) {
            totalAmount = prices[1];
            body = Constant.buyTypeMap.get(2);
        } else if (type==3) {
            totalAmount = prices[2];
            body = Constant.buyTypeMap.get(3);
        } else {
            // 无效的请求参数
            httpResponse.setContentType("text/html;charset=" + Constant.charset);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("code", 11);
            jsonObject.put("data", null);
            jsonObject.put("status", 200);
            jsonObject.put("msg", "无效的请求参数");
            httpResponse.getWriter().write(jsonObject.toJSONString());
            httpResponse.getWriter().flush();
            httpResponse.getWriter().close();
            return;
        }
        if (mvo.getId()==3 || mvo.getId()==5) {
            totalAmount = "0.01";
        }

        Order order = new Order();
        order.setBuyType(type);
        order.setPayType(1);
        order.setFlowId(flowId);
        order.setOrderNo(outTradeNo);
        order.setMerchantId(mvo.getId());
        order.setOpeUser(mvo.getOpeUser());
        order.setMoney(Double.valueOf(totalAmount));
        orderMapper.insOrder(order);
        log.info("# pay merchant:"+mvo.getId()+",ali orderNo:"+outTradeNo+",order:"+order);

        //获得初始化的AlipayClient 向支付宝发送支付请求
        AlipayClient alipayClient = new DefaultAlipayClient(Constant.gatewayUrl, Constant.alipay_app_id,
                Constant.alipay_private_key, AlipayConstants.FORMAT_JSON, Constant.charset, Constant.alipay_public_key, Constant.sign_type);
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
        boolean signVerified = AlipaySignature.rsaCheckV1(paramsMap, Constant.alipay_public_key, Constant.charset, Constant.sign_type); // 调用SDK验证签名

        String order_no = request.getParameter("out_trade_no"); // 获取订单号
        String sessionId = request.getSession().getId();
        String trade_no = request.getParameter("trade_no"); // 支付宝交易号
        String total_fee = request.getParameter("total_amount"); // 用户支付金额
        String buyer_email = request.getParameter("buyer_email"); // 买家支付宝账号
        String buyerLogonId =request.getParameter("buyer_logon_id");//买家支付宝账号
        String trade_status = request.getParameter("trade_status"); // 交易状态
        String passback_params = request.getParameter("passback_params"); // 交易状态

        log.info("# pay ali orderNo:"+order_no+",trade_no:"+trade_no+",trade_status:"+trade_status);
        PrintWriter out = response.getWriter();
        if (signVerified) {
            if (trade_status.equals("TRADE_FINISHED") || trade_status.equals("TRADE_SUCCESS")) {
                Order order = orderMapper.getOrderByNo(order_no);
                if (order==null) {
                    out.println("交易失败,订单号不存在order_no:"+order_no);
                    return;
                }
                if (StringUtils.isNotBlank(order.getTradeNo())) {
                    out.println("success");
                }
                order.setTradeNo(trade_no);
                order.setUt(DateUtil.getTime());
                order.setStatus(Constant.pay_success);
                orderMapper.updateSuccess(order);

                successHandler(order);
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
    public <T> T wxPay(Integer type, String flowId, HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws IOException, WxPayException {
        MerchantVO mvo = UserUtil.getMerchant();
        if (mvo ==null) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("data", null);
            jsonObject.put("status", 200);
            jsonObject.put("msg", BaseEnum.No_Login.getMsg());
            jsonObject.put("code", BaseEnum.No_Login.getCode());
            httpResponse.setContentType("text/html;charset=" + Constant.charset);
            httpResponse.getWriter().write(jsonObject.toJSONString());
            httpResponse.getWriter().flush();
            httpResponse.getWriter().close();
            return (T) new BaseResult<>(httpResponse);
        }

        String totalAmount;
        String subject = Constant.buy_subject;
        String outTradeNo = GeneralUtil.getOrderNo(type)+"W";
        String sessionId = httpRequest.getSession().getId();

        String data = dataConfigMapper.getConfigStr(Constant.admin_id);
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
            jsonObject.put("code", 11);
            jsonObject.put("data", null);
            jsonObject.put("status", 200);
            jsonObject.put("msg", "无效的请求参数");
            httpResponse.setContentType("text/html;charset=" + Constant.charset);
            httpResponse.getWriter().write(jsonObject.toJSONString());
            httpResponse.getWriter().flush();
            httpResponse.getWriter().close();
            return (T) new BaseResult<>(httpResponse);
        }
        if (mvo.getId()==3 || mvo.getId()==5) {
            totalAmount = "0.01";
        }

        Order order = new Order();
        order.setPayType(2);
        order.setBuyType(type);
        order.setFlowId(flowId);
        order.setOrderNo(outTradeNo);
        order.setMerchantId(mvo.getId());
        order.setOpeUser(mvo.getOpeUser());
        order.setMoney(Double.valueOf(totalAmount));
        orderMapper.insOrder(order);
        log.info("# pay merchant:"+mvo.getId()+",wx orderNo:"+outTradeNo+",order:"+order);


        // 调用微信支付接口
        WxPayUnifiedOrderRequest request = new WxPayUnifiedOrderRequest();
        request.setBody(subject);
        request.setDetail(subject);
        request.setProductId("1");
        request.setOutTradeNo(outTradeNo);
        // 终端ip：Native支付填写调用微信支付API的机器ip
        request.setSpbillCreateIp("43.142.141.181");
//        // 设置支付过期时间：30分钟
//        request.setTimeExpire(getOrderExpireTime(30*sec));
        // 微信支付的金额是不能带小数点的，乘以100提交，因为里面设置参数的时候是以"分"为单位的
        // 订单金额，单位为分
        request.setTotalFee((int) (NumberUtils.toFloat(totalAmount) * 100));
        request.setNotifyUrl(Constant.notify_url2);//线上回调地址
        request.setAttach(sessionId);//附加数据sessionId
        request.setTradeType("NATIVE"); //网页支付

        Object jo = this.wxService.createOrder(request);
        Map<String, Object> map = Maps.newHashMap();
        map.put("codeUrl", jo);
        map.put("orderNo", outTradeNo);
        return (T) new BaseResult<>(map);
    }

    /**
     * 微信异步回调接口
     */
    @PostMapping("/notify/wx")
    public String wxNotifyPay(@RequestBody String xmlData) throws WxPayException {
        final WxPayOrderNotifyResult notifyResult = this.wxService.parseOrderNotifyResult(xmlData);

        String trade_no = notifyResult.getTransactionId(); // 交易号
        String order_no = notifyResult.getOutTradeNo(); // 获取订单号
        long total_fee = (long) notifyResult.getTotalFee() / 100; // 用户支付金额
        String attach = notifyResult.getAttach(); // 交易状态

        log.info("# pay wx orderNo:"+order_no+",trade_no:"+trade_no+",trade_status:"+attach);

        Order order = orderMapper.getOrderByNo(order_no);
        if (StringUtils.isNotBlank(order.getTradeNo())) {
            return WxPayNotifyResponse.success("支付成功");
        }
        order.setTradeNo(trade_no);
        order.setUt(DateUtil.getTime());
        order.setStatus(Constant.pay_success);
        orderMapper.updateSuccess(order);

        successHandler(order);
        return WxPayNotifyResponse.success("支付成功");
    }

}
