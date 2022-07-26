package com.example.live.util;


import com.alibaba.fastjson.JSONObject;
import com.example.live.common.Constant;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.ess.v20201111.EssClient;
import com.tencentcloudapi.ess.v20201111.models.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;


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
    private static final String templateId_company = "yDRt4UU2b31h4Uy0fKyKRJeoaOzglL9w";

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
        String filename = GeneralUtil.get4Random()+"-"+Constant.buy_subject;
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
        return signProcess4();
    }
    // 合同文件下载
    public String signDown(String documentId) {
        return singFileDown(documentId);
    }

    public static void main(String[] args) {
        JSONObject jo = new JSONObject();
        jo.put("type", 1);
        jo.put("person", "白帅雷");
        jo.put("mobile", "15394249033");
        jo.put("subject", "白白白电子商务有限公司");
        jo.put("filename", Constant.buy_subject);

//        signProcess1(jo);

//        signProcess2("yDRt4UU2b7m6zUy0fKyKuLs64uCHFE0R", Constant.sign_name, 1);
        // DocumentId=yDRt4UU2bxhioUy0fKyK1AEef4strMLR

//        signProcess3("yDRt4UU2b7m6zUy0fKyKuLs64uCHFE0R");

//        signProcess4();

        signTemplateInfo(templateId_person);
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

            FlowCreateApprover[] flowCreateApprovers1 = new FlowCreateApprover[1];

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
            flowCreateApprover1.setPreReadTime(15L);
            // 当前只支持true、默认true
            flowCreateApprover1.setRequired(true);
            flowCreateApprovers1[0] = flowCreateApprover1;

            // 签署流程参与者信息
            req.setApprovers(flowCreateApprovers1);
            req.setOperator(getUserInfo());

            // 返回的resp是一个CreateFlowResponse的实例，与请求对象对应
            CreateFlowResponse resp = essClient.CreateFlow(req);
            // 输出json格式的字符串回包
            System.out.println("#signProcess1:"+CreateFlowResponse.toJsonString(resp));
            return resp.getFlowId();
        } catch (TencentCloudSDKException e) {
            e.printStackTrace();
        }
        return null;
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
            // 乙方：法人：componentId_12、手机号：componentId_13、签订时间：componentId_3
            // 抖店名称：componentId_14、抖店ID：componentId_15、抖店主体名称：componentId_16
            // 服务合作费用：componentId_17、服务合作时间：componentId_18、乙方签字：componentId_20
            // 签署人姓名：componentId_22、签署人手机号：componentId_24、签署时间：componentId_19
            //
            // 甲方：日期：componentId_4、电话：componentId_8、法人：componentId_7、签字：componentId_6

            // 个人签
            // 乙方：法人：componentId_1、手机号：componentId_2、签订时间：componentId_12
            // 抖店名称：componentId_3、抖店ID：componentId_4、抖店主体名称：componentId_5
            // 服务合作费用：componentId_6、服务合作时间：componentId_7、乙方签字：componentId_8
            // 签署人姓名：componentId_11、签署人手机号：componentId_10、签署时间：componentId_9
            //
            // 甲方：日期：componentId_16、电话：componentId_15、法人：componentId_14、签字：componentId_13

            String person = jo.getString("person");
            String mobile = jo.getString("mobile");
            String subject = jo.getString("subject");
            String fee = jo.getString("fee");
            String shop = jo.getString("shop");
            String shopId = jo.getString("shopId");
            String days = GeneralUtil.typeDays(jo.getIntValue("buyType"))+"";

            if (type==1) {
                req.setTemplateId(templateId_person);
                FormField[] formFields = new FormField[16];
                // 乙方
                FormField fb0 = new FormField();
                fb0.setComponentId("componentId_1");
                fb0.setComponentValue(person);
                FormField fb1 = new FormField();
                fb1.setComponentId("componentId_2");
                fb1.setComponentValue(mobile);
                FormField fb2 = new FormField();
                fb2.setComponentId("componentId_12");
                fb2.setComponentValue(date);
                FormField fb3 = new FormField();
                fb3.setComponentId("componentId_3");
                fb3.setComponentValue(shop);
                FormField fb4 = new FormField();
                fb4.setComponentId("componentId_4");
                fb4.setComponentValue(shopId);
                FormField fb5 = new FormField();
                fb5.setComponentId("componentId_5");
                fb5.setComponentValue(subject);
                FormField fb6 = new FormField();
                fb6.setComponentId("componentId_6");
                fb6.setComponentValue(fee);
                FormField fb7 = new FormField();
                fb7.setComponentId("componentId_7");
                fb7.setComponentValue(days);
                FormField fb9 = new FormField();
                fb9.setComponentId("componentId_9");
                fb9.setComponentValue(date);
                FormField fb10 = new FormField();
                fb10.setComponentId("componentId_10");
                fb10.setComponentValue(mobile);
                FormField fb11 = new FormField();
                fb11.setComponentId("componentId_11");
                fb11.setComponentValue(person);

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

                // 甲方
                FormField fa13 = new FormField();
                fa13.setComponentId("componentId_13");
                fa13.setComponentValue(Constant.name);

                FormField fa14 = new FormField();
                fa14.setComponentId("componentId_14");
                fa14.setComponentValue(Constant.legal_person);

                FormField fa15 = new FormField();
                fa15.setComponentId("componentId_15");
                fa15.setComponentValue(Constant.legal_mobile);

                FormField fa16 = new FormField();
                fa16.setComponentId("componentId_16");
                fa16.setComponentValue(date);

                formFields[12] = fa13;
                formFields[13] = fa14;
                formFields[14] = fa15;
                formFields[15] = fa16;

                req.setFormFields(formFields);
            } else {
                req.setTemplateId(templateId_company);
                FormField[] formFields = new FormField[16];
                // 乙方
                FormField fb0 = new FormField();
                fb0.setComponentId("componentId_12");
                fb0.setComponentValue(person);
                FormField fb1 = new FormField();
                fb1.setComponentId("componentId_13");
                fb1.setComponentValue(mobile);
                FormField fb2 = new FormField();
                fb2.setComponentId("componentId_3");
                fb2.setComponentValue(date);
                FormField fb3 = new FormField();
                fb3.setComponentId("componentId_14");
                fb3.setComponentValue(shop);
                FormField fb4 = new FormField();
                fb4.setComponentId("componentId_15");
                fb4.setComponentValue(shopId);
                FormField fb5 = new FormField();
                fb5.setComponentId("componentId_16");
                fb5.setComponentValue(subject);
                FormField fb6 = new FormField();
                fb6.setComponentId("componentId_17");
                fb6.setComponentValue(fee);
                FormField fb7 = new FormField();
                fb7.setComponentId("componentId_18");
                fb7.setComponentValue(days);
                FormField fb9 = new FormField();
                fb9.setComponentId("componentId_22");
                fb9.setComponentValue(person);
                FormField fb10 = new FormField();
                fb10.setComponentId("componentId_24");
                fb10.setComponentValue(mobile);
                FormField fb11 = new FormField();
                fb11.setComponentId("componentId_19");
                fb11.setComponentValue(date);

                formFields[0] = fb0;
                formFields[1] = fb1;
                formFields[2] = fb2;
                formFields[3] = fb3;
                formFields[5] = fb4;
                formFields[6] = fb5;
                formFields[7] = fb6;
                formFields[8] = fb7;
                formFields[9] = fb9;
                formFields[10] = fb10;
                formFields[11] = fb11;

                // 甲方
                FormField fa13 = new FormField();
                fa13.setComponentId("componentId_6");
                fa13.setComponentValue(Constant.name);

                FormField fa14 = new FormField();
                fa14.setComponentId("componentId_7");
                fa14.setComponentValue(Constant.legal_person);

                FormField fa15 = new FormField();
                fa15.setComponentId("componentId_8");
                fa15.setComponentValue(Constant.legal_mobile);

                FormField fa16 = new FormField();
                fa16.setComponentId("componentId_4");
                fa16.setComponentValue(date);

                formFields[12] = fa13;
                formFields[13] = fa14;
                formFields[14] = fa15;
                formFields[15] = fa16;

                req.setFormFields(formFields);
            }
            req.setOperator(getUserInfo());

            // 返回的resp是一个CreateDocumentResponse的实例，与请求对象对应
            CreateDocumentResponse resp = essClient.CreateDocument(req);
            // 输出json格式的字符串回包
            System.out.println("#signProcess2:"+CreateDocumentResponse.toJsonString(resp));
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
            System.out.println("#signProcess3:"+StartFlowResponse.toJsonString(resp));
            return resp.getStatus();
        } catch (TencentCloudSDKException e) {
            e.printStackTrace();
        }
        return null;
    }

    // 第四步：获取小程序跳转链接
    // 返回：
    // SchemeUrl:小程序链接地址
    // RequestId
    public static String signProcess4() {
        try {
            // 实例化一个请求对象,每个接口都会对应一个request对象
            CreateSchemeUrlRequest req = new CreateSchemeUrlRequest();
            req.setOperator(getUserInfo());

            // 返回的resp是一个CreateSchemeUrlResponse的实例，与请求对象对应
            CreateSchemeUrlResponse resp = essClient.CreateSchemeUrl(req);
            // 输出json格式的字符串回包
            System.out.println("#signProcess4:"+CreateSchemeUrlResponse.toJsonString(resp));
            return QRCodeUtil.code(resp.getSchemeUrl());
        } catch (TencentCloudSDKException e) {
            e.printStackTrace();
        }
        return null;
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
            System.out.println("#signFileDown:"+DescribeFileUrlsResponse.toJsonString(resp));
            return resp.getFileUrls()[0].getUrl();
        } catch (TencentCloudSDKException e) {
            e.printStackTrace();
        }
        return null;
    }

    // 模板查询接口
    public static void signTemplateInfo(String templateId) {
        try{
            // 实例化一个请求对象,每个接口都会对应一个request对象
            DescribeFlowTemplatesRequest req = new DescribeFlowTemplatesRequest();
            req.setOperator(getUserInfo());

            Filter[] filters = new Filter[1];
            Filter filter = new Filter();
            filter.setKey("template-id");
            String[] val = {templateId};
            filter.setValues(val);
            filters[0] = filter;
            req.setFilters(filters);

            // 返回的resp是一个DescribeFlowTemplatesResponse的实例，与请求对象对应
            DescribeFlowTemplatesResponse resp = essClient.DescribeFlowTemplates(req);
            // 输出json格式的字符串回包
            System.out.println(DescribeFlowTemplatesResponse.toJsonString(resp));
        } catch (TencentCloudSDKException e) {
            e.printStackTrace();
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
