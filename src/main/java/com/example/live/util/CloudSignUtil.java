package com.example.live.util;


import com.alibaba.fastjson.JSONObject;
import com.example.live.common.Constant;
import com.google.common.collect.Maps;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.ess.v20201111.EssClient;
import com.tencentcloudapi.ess.v20201111.models.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;


/**
 * 腾讯云操作工具类
 *
 * @author baishuailei@zhejianglab.com
 * @date 2022/7/15 14:19
 */
@Component
public class CloudSignUtil {

    // 个人签
    private static final String templateId_person = "yDRt4UU2b31h4Uy0fKyKRJeoaOzglL9w";

    // 企业签
    private static final String templateId_company = "yDRt4UU2b39n5Uy0fKyKS4R0Z7M5jOvE";

    // userId
    private static final String userInfoId = "yDRt3UU2we0a3Uy0fKyKS9OOC4BVSaWC";

    private static final EssClient essClient = getEssClient();

    private static EssClient getEssClient() {
        if (essClient != null) {
            return essClient;
        }
        return essClientInit();
    }

    // 构建client
    private static EssClient essClientInit() {
        // 实例化一个认证对象，入参需要传入腾讯云账户secretId，secretKey,此处还需注意密钥对的保密
        // 密钥可前往https://console.cloud.tencent.com/cam/capi网站进行获取
        Credential cred = new Credential(Constant.cloud_secretId, Constant.cloud_secretKey);
        // 实例化一个http选项，可选的，没有特殊需求可以跳过
        HttpProfile httpProfile = new HttpProfile();
        httpProfile.setEndpoint("ess.tencentcloudapi.com");
        // 实例化一个client选项，可选的，没有特殊需求可以跳过
        ClientProfile clientProfile = new ClientProfile();
        clientProfile.setHttpProfile(httpProfile);
        // 实例化要请求产品的client对象,clientProfile是可选的
        return new EssClient(cred, "", clientProfile);
    }

    // 先获取签署预览
    public JSONObject signPreview(JSONObject jo) {
        String filename = GeneralUtil.get4Random()+Constant.buy_subject;
        jo.put("filename", filename);

        String flowId = signProcess1(jo);
        if (StringUtils.isNotBlank(flowId)) {
            jo.put("flowId", flowId);

            JSONObject jo2 = signProcess2(jo);
            jo2.put("flowId", flowId);
            jo2.put("filename", filename);
            return jo2;
        }
        return null;
    }
    // 获取签署链接
    public String signUrl(String flowId) {
        String result = signProcess3(flowId);
        return signProcess4(flowId);
    }
    public String signSingleUrl(String flowId) {
        return signProcess4(flowId);
    }
    // 签署状态
    public boolean signStatus(String flowId) {
        return signProcess5(flowId);
    }
    // 合同文件下载
    public String signDown(String documentId) {
        return singFileDown(documentId);
    }


    /*电子签 https://cloud.tencent.com/document/product/1323/70361*/
    // 第一步：创建签署流程
    // 返回：
    // flowId:签署流程编号、requestId
    // 入参：type、person、mobile、subject、filename
    public static String signProcess1(JSONObject jo) {
        Integer type = jo.getInteger("type");
        String person = jo.getString("person");
        String mobile = jo.getString("mobile");
        String subject = jo.getString("subject");
        String filename = jo.getString("filename");

        try {
            // 实例化一个请求对象,每个接口都会对应一个request对象
            CreateFlowRequest req = new CreateFlowRequest();
            // 签署流程名称
            req.setFlowName(filename);
            // 签署流程的签署截止时间
            String date = DateUtil.getDateDay(DateUtil.getTime(), 7, "yyyy-MM-dd HH:mm:ss");
            long tamp = DateUtil.dateToStamp(date, "yyyy-MM-dd HH:mm:ss")/1000;
            req.setDeadLine(tamp);
            // 合同类型
            req.setFlowType(Constant.buy_subject);

            FlowCreateApprover[] flowCreateApprovers1 = new FlowCreateApprover[2];

            // 创建签署流程的签署方信息
            FlowCreateApprover flowCreateApprover1 = new FlowCreateApprover();
            // 参与者类型：0-企业、1-个人、3-企业静默签署（接口会默认完成该签署方的签署）
            flowCreateApprover1.setApproverType(Long.valueOf(type));
            if (type==0) {
                // 企业签
                // 如果签署方为企业、需要填入企业全称
                flowCreateApprover1.setOrganizationName(subject);
            }
            // 签署方经办人姓名
            flowCreateApprover1.setApproverName(person);
            // 签署方经办人手机号
            flowCreateApprover1.setApproverMobile(mobile);

            // 签署前置条件：是否需要阅读全文，默认为不需要
            flowCreateApprover1.setIsFullText(true);
            // 签署前置条件：阅读时长限制，默认不需要
            flowCreateApprover1.setPreReadTime(10L);
            // 当前只支持true、默认true
            flowCreateApprover1.setRequired(true);

            // 签署方：甲方
            FlowCreateApprover flowCreateApprover0 = new FlowCreateApprover();
            flowCreateApprover0.setApproverType(3L);
            flowCreateApprover0.setRequired(true);

            // 注意模板设置的签署顺序
            flowCreateApprovers1[0] = flowCreateApprover0;
            flowCreateApprovers1[1] = flowCreateApprover1;

            // 签署流程参与者信息
            req.setApprovers(flowCreateApprovers1);
            req.setOperator(getUserInfo());

            // 返回的resp是一个CreateFlowResponse的实例，与请求对象对应
            CreateFlowResponse resp = essClient.CreateFlow(req);
            // 输出json格式的字符串回包
            return resp.getFlowId();
        } catch (TencentCloudSDKException e) {
            e.printStackTrace();
            return null;
        }
    }

    // 第二步：创建电子文档
    // 返回：
    // DocumentId:签署流程电子文档ID、PreviewFileUrl:签署流程文件的预览地址，5分钟有效
    // 仅当NeedPreview为true时返回，注意：此字段可能为空，表示取不到有效值
    // 入参jo：type、flowId、filename、person、mobile、subject、fee、shop、shopId、buyType
    public static JSONObject signProcess2(JSONObject jo) {
        JSONObject jo2 = new JSONObject();
        try {
            int type = jo.getIntValue("type");
            String date = DateUtil.dateTime();
            // 实例化一个请求对象,每个接口都会对应一个request对象
            CreateDocumentRequest req = new CreateDocumentRequest();
            // 签署流程编号，由CreateFlow接口返回
            req.setFlowId(jo.getString("flowId"));

            String[] filename = {jo.getString("filename")};
            // 文件名列表
            req.setFileNames(filename);
            req.setNeedPreview(true);

            // 企业签
            // 乙方：
            // 乙方签字：componentId_20
            // 发起方：法人：componentId_1、手机号：componentId_2、签订时间：componentId_3
            // 抖店名称：componentId_5、抖店ID：componentId_13、抖店主体名称：componentId_14
            // 服务合作权益：componentId_21、服务合作月份：componentId_22、
            // 签署人姓名：componentId_13、签署人手机号：componentId_14、签署时间：componentId_15
            // 日期：componentId_20、印章：componentId_19
            // 合同编号：componentId_23
            // 甲方：日期：componentId_18、印章：componentId_17

            // 个人签
            // 乙方签字：componentId_5
            // 发起方：法人：componentId_25、手机号：componentId_26、签订时间：componentId_27
            // 抖店名称：componentId_28、抖店ID：componentId_1、抖店主体名称：componentId_2
            // 服务合作权益：componentId_3、服务合作时间：componentId_4、
            // 签署人姓名：componentId_8、签署人手机号：componentId_7、签署时间：componentId_6
            // 甲方：日期：componentId_10、印章：componentId_9

            int buyType0 = jo.getIntValue("buyType");
            String person = jo.getString("person");
            String mobile = jo.getString("mobile");
            String subject = jo.getString("subject");
            String buyType = Constant.buyTypeMap.get(buyType0);
            String shop = jo.getString("shop");
            String shopId = jo.getString("shopId");
            String months = GeneralUtil.typeMonths(buyType0)+"";
            String signNo = DateUtil.getTime2()+GeneralUtil.get4Random();

            if (type==1) {
                req.setTemplateId(templateId_person);
                FormField[] formFields = new FormField[13];
                FormField fb0 = new FormField();
                fb0.setComponentId("componentId_25");
                fb0.setComponentValue(person);
                FormField fb1 = new FormField();
                fb1.setComponentId("componentId_26");
                fb1.setComponentValue(mobile);
                FormField fb2 = new FormField();
                fb2.setComponentId("componentId_27");
                fb2.setComponentValue(date);
                FormField fb3 = new FormField();
                fb3.setComponentId("componentId_28");
                fb3.setComponentValue(shop);
                FormField fb4 = new FormField();
                fb4.setComponentId("componentId_1");
                fb4.setComponentValue(shopId);
                FormField fb5 = new FormField();
                fb5.setComponentId("componentId_2");
                fb5.setComponentValue(subject);
                FormField fb6 = new FormField();
                fb6.setComponentId("componentId_3");
                fb6.setComponentValue(buyType);
                FormField fb7 = new FormField();
                fb7.setComponentId("componentId_4");
                fb7.setComponentValue(months);
                FormField fb9 = new FormField();
                fb9.setComponentId("componentId_8");
                fb9.setComponentValue(person);
                FormField fb10 = new FormField();
                fb10.setComponentId("componentId_7");
                fb10.setComponentValue(mobile);
                FormField fb11 = new FormField();
                fb11.setComponentId("componentId_6");
                fb11.setComponentValue(date);
                FormField fb12 = new FormField();
                fb12.setComponentId("componentId_11");
                fb12.setComponentValue(signNo);
                FormField fb13 = new FormField();
                fb13.setComponentId("componentId_24");
                fb13.setComponentValue(subject);

                formFields[0] = fb0;
                formFields[1] = fb1;
                formFields[2] = fb2;
                formFields[3] = fb3;
                formFields[4] = fb4;
                formFields[5] = fb5;
                formFields[6] = fb6;
                formFields[7] = fb7;
                formFields[8] = fb9;
                formFields[9] = fb10;
                formFields[10] = fb11;
                formFields[11] = fb12;
                formFields[12] = fb13;
                req.setFormFields(formFields);
            } else {
                req.setTemplateId(templateId_company);
                FormField[] formFields = new FormField[10];
                // 乙方
                FormField fb0 = new FormField();
                fb0.setComponentId("componentId_1");
                fb0.setComponentValue(person);
                FormField fb1 = new FormField();
                fb1.setComponentId("componentId_2");
                fb1.setComponentValue(mobile);
                FormField fb2 = new FormField();
                fb2.setComponentId("componentId_3");
                fb2.setComponentValue(date);
                FormField fb3 = new FormField();
                fb3.setComponentId("componentId_5");
                fb3.setComponentValue(shop);
                FormField fb4 = new FormField();
                fb4.setComponentId("componentId_13");
                fb4.setComponentValue(shopId);
                FormField fb5 = new FormField();
                fb5.setComponentId("componentId_14");
                fb5.setComponentValue(subject);
                FormField fb6 = new FormField();
                fb6.setComponentId("componentId_21");
                fb6.setComponentValue(buyType);
                FormField fb7 = new FormField();
                fb7.setComponentId("componentId_22");
                fb7.setComponentValue(months);
                FormField fb12 = new FormField();
                fb12.setComponentId("componentId_23");
                fb12.setComponentValue(signNo);
                FormField fb13 = new FormField();
                fb13.setComponentId("componentId_25");
                fb13.setComponentValue(subject);

                formFields[0] = fb0;
                formFields[1] = fb1;
                formFields[2] = fb2;
                formFields[3] = fb3;
                formFields[4] = fb4;
                formFields[5] = fb5;
                formFields[6] = fb6;
                formFields[7] = fb7;
                formFields[8] = fb12;
                formFields[9] = fb13;
                req.setFormFields(formFields);
            }
            req.setOperator(getUserInfo());

            // 返回的resp是一个CreateDocumentResponse的实例，与请求对象对应
            CreateDocumentResponse resp = essClient.CreateDocument(req);
            // 输出json格式的字符串回包
            jo2.put("documentId", resp.getDocumentId());
            jo2.put("previewUrl", resp.getPreviewFileUrl());
        } catch (TencentCloudSDKException e) {
            e.printStackTrace();
        }
        return jo2;
    }

    // 第三步：发起流程
    // 返回：
    // Status:返回描述，START-发起成功， REVIEW-提交审核成功，EXECUTING-已提交发起任务
    // RequestId
    public static String signProcess3(String flowId) {
        try {
            // 实例化一个请求对象,每个接口都会对应一个request对象
            StartFlowRequest req = new StartFlowRequest();
            // 签署流程编号，由CreateFlow接口返回
            req.setFlowId(flowId);
            req.setOperator(getUserInfo());

            // 返回的resp是一个StartFlowResponse的实例，与请求对象对应
            StartFlowResponse resp = essClient.StartFlow(req);
            // 输出json格式的字符串回包
            return resp.getStatus();
        } catch (TencentCloudSDKException e) {
            e.printStackTrace();
            return null;
        }
    }

    // 第四步：获取小程序跳转链接
    // 返回：
    // SchemeUrl:小程序链接地址
    // RequestId
    public static String signProcess4(String flowId) {
        try {
            // 实例化一个请求对象,每个接口都会对应一个request对象
            CreateSchemeUrlRequest req = new CreateSchemeUrlRequest();
            req.setOperator(getUserInfo());
            // pathType=1是必传
            req.setFlowId(flowId);
            // 跳转页面：1-小程序合同详情、2-小程序合同列表、0-不传，默认首页
            req.setPathType(1L);

            // 返回的resp是一个CreateSchemeUrlResponse的实例，与请求对象对应
            CreateSchemeUrlResponse resp = essClient.CreateSchemeUrl(req);
            // 输出json格式的字符串回包
            return QRCodeUtil.code(resp.getSchemeUrl());
        } catch (TencentCloudSDKException e) {
            e.printStackTrace();
            return null;
        }
    }

    // 查询流程
    // 返回：FlowStatus 流程状态
    //- 1 未签署
    //- 2 部分签署
    //- 3 已退回
    //- 4 完成签署
    //- 5 已过期
    //- 6 已取消
    //注意：此字段可能返回 null，表示取不到有效值
    public static boolean signProcess5(String flowId) {
        boolean result = false;
        try{
            // 实例化一个请求对象,每个接口都会对应一个request对象
            DescribeFlowBriefsRequest req = new DescribeFlowBriefsRequest();
            String[] flowIds1 = {flowId};
            req.setFlowIds(flowIds1);
            req.setOperator(getUserInfo());
            // 返回的resp是一个DescribeFlowBriefsResponse的实例，与请求对象对应
            DescribeFlowBriefsResponse resp = essClient.DescribeFlowBriefs(req);
            // 输出json格式的字符串回包
            Long flowStatus = resp.getFlowBriefs()[0].getFlowStatus();
            if (flowStatus!=null && flowStatus==4) {
                result = true;
            }
        } catch (TencentCloudSDKException e) {
            e.printStackTrace();
        }
        return result;
    }

    public Map<String, String> signStatusMap(List<String> flowIds) {
        if (flowIds==null || flowIds.size()==0) {
            return null;
        }
        Map<String, String> map = Maps.newHashMap();
        try{
            // 实例化一个请求对象,每个接口都会对应一个request对象
            DescribeFlowBriefsRequest req = new DescribeFlowBriefsRequest();
            req.setFlowIds(flowIds.toArray(new String[0]));
            req.setOperator(getUserInfo());
            // 返回的resp是一个DescribeFlowBriefsResponse的实例，与请求对象对应
            DescribeFlowBriefsResponse resp = essClient.DescribeFlowBriefs(req);
            // 输出json格式的字符串回包
            for (FlowBrief flowBrief:resp.getFlowBriefs()) {
                String value = "未签";
                if (flowBrief.getFlowStatus()!=null&&flowBrief.getFlowStatus()==4) {
                    value = "已签";
                }
                map.put(flowBrief.getFlowId(), value);
            }
        } catch (TencentCloudSDKException e) {
            e.printStackTrace();
        }
        return map;
    }

    // 文件下载
    public static String singFileDown(String documentId) {
        try{
            // 实例化一个请求对象,每个接口都会对应一个request对象
            DescribeFileUrlsRequest req = new DescribeFileUrlsRequest();
            // 文件对应的业务类型：模板 "TEMPLATE"、流程 "FLOW"、文档 "DOCUMENT"
            req.setBusinessType("DOCUMENT");
            // 业务编号的数组，如模板id、流程id、文档id
            String[] val = {documentId};
            req.setBusinessIds(val);
            req.setOperator(getUserInfo());

            // 返回的resp是一个DescribeFileUrlsResponse的实例，与请求对象对应
            DescribeFileUrlsResponse resp = essClient.DescribeFileUrls(req);
            // 输出json格式的字符串回包
            if (resp.getFileUrls()==null||resp.getFileUrls().length==0) {
                return null;
            }
            return resp.getFileUrls()[0].getUrl();
        } catch (TencentCloudSDKException e) {
            e.printStackTrace();
            return null;
        }
    }

    // 用户信息
    private static UserInfo getUserInfo() {
        UserInfo userInfo = new UserInfo();
        // 用户在平台的编号
        userInfo.setUserId(userInfoId);
        return userInfo;
    }

}
