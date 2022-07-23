package com.example.live.util;


import com.alibaba.fastjson.JSONObject;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.ess.v20201111.EssClient;
import com.tencentcloudapi.ess.v20201111.models.*;
import org.springframework.stereotype.Component;


/**
 * 腾讯云操作工具类
 *
 * @author baishuailei@zhejianglab.com
 * @date 2022/7/15 14:19
 */
@Component
public class CloudSignUtil {

    private static final String secretId = "AKIDLWTBdqDMGvKYWHm35sPiogIfoiMlvqVu";
    private static final String secretKey = "DBtMaaLq55sRlqfFZt52kDRQAetteARS";
    private static final String templateId = "";

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
        Credential cred = new Credential(secretId, secretKey);
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
        Integer cid = jo.getInteger("cid");
        Integer mid = jo.getInteger("mid");

        String filename = "直播带货服务合作签署合同"+cid;
        jo.put("filename", filename);

        String flowId = signProcess1(jo);
        JSONObject jo2 = signProcess2(flowId, filename);
        jo2.put("cid", cid);
        jo2.put("mid", mid);
        jo2.put("flowId", flowId);
        jo2.put("filename", filename);
        return jo2;
    }
    // 获取签署链接
    public String signAgree(String flowId) {
        return signProcess3(flowId);
    }
    // 获取签署链接
    public String signUrl() {
        return signProcess4();
    }


    /*电子签 https://cloud.tencent.com/document/product/1323/70361*/
    // 第一步：创建签署流程
    // 返回：
    // flowId:签署流程编号、requestId
    public String signProcess1(JSONObject jo) {
        String tax = jo.getString("tax");
        Integer type = jo.getInteger("type");
        String owner = jo.getString("owner");
        String mobile = jo.getString("mobile");
        String company = jo.getString("company");
        String filename = jo.getString("filename");

        try {
            // 实例化一个请求对象,每个接口都会对应一个request对象
            CreateFlowRequest req = new CreateFlowRequest();
            // 签署流程名称
            req.setFlowName(filename);

            FlowCreateApprover[] flowCreateApprovers1 = new FlowCreateApprover[1];
            // 创建签署流程的签署方信息
            FlowCreateApprover flowCreateApprover1 = new FlowCreateApprover();
            // 参与者类型：0-企业、1-个人、3-企业静默签署（接口会默认完成该签署方的签署）
            flowCreateApprover1.setApproverType(Long.valueOf(type));
            // 如果签署方为企业、需要填入企业全称
            flowCreateApprover1.setOrganizationName(company);
            // 签署方经办人姓名
            flowCreateApprover1.setApproverName(owner);
            // 签署方经办人手机号
            flowCreateApprover1.setApproverMobile(mobile);
            // 签署方经办人证件类型 ID_CARD-身份证
            flowCreateApprover1.setApproverIdCardType("ID_CARD");
            // 签署方经办人证件号码
            flowCreateApprover1.setApproverIdCardNumber(tax);

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
    // RequestId:唯一请求id，每次请求都会返回，定位问题时需要提供该次请求的id
    public JSONObject signProcess2(String flowId, String filename) {
        JSONObject jo = new JSONObject();
        try {
            // 实例化一个请求对象,每个接口都会对应一个request对象
            CreateDocumentRequest req = new CreateDocumentRequest();
            // 签署流程编号，由CreateFlow接口返回
            req.setFlowId(flowId);
            // 用户上传的模板ID
            req.setTemplateId(templateId);

            String[] fileNames1 = {filename};
            // 文件名列表
            req.setFileNames(fileNames1);
            req.setOperator(getUserInfo());

            // 返回的resp是一个CreateDocumentResponse的实例，与请求对象对应
            CreateDocumentResponse resp = essClient.CreateDocument(req);
            // 输出json格式的字符串回包
            System.out.println("#signProcess2:"+CreateDocumentResponse.toJsonString(resp));
            jo.put("documentId", resp.getDocumentId());
            jo.put("previewUrl", resp.getPreviewFileUrl());
        } catch (TencentCloudSDKException e) {
            e.printStackTrace();
        }
        return jo;
    }

    // 第三步：发起流程
    // 返回：
    // Status:返回描述，START-发起成功， REVIEW-提交审核成功，EXECUTING-已提交发起任务
    // RequestId
    public String signProcess3(String flowId) {
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
    public String signProcess4() {
        try {
            // 实例化一个请求对象,每个接口都会对应一个request对象
            CreateSchemeUrlRequest req = new CreateSchemeUrlRequest();
            req.setOperator(getUserInfo());

            // 返回的resp是一个CreateSchemeUrlResponse的实例，与请求对象对应
            CreateSchemeUrlResponse resp = essClient.CreateSchemeUrl(req);
            // 输出json格式的字符串回包
            System.out.println("#signProcess4:"+CreateSchemeUrlResponse.toJsonString(resp));
            return resp.getSchemeUrl();
        } catch (TencentCloudSDKException e) {
            e.printStackTrace();
        }
        return null;
    }

    // 文件下载
    public String singFileDown() {
        try{
            // 实例化一个请求对象,每个接口都会对应一个request对象
            DescribeFileUrlsRequest req = new DescribeFileUrlsRequest();
            // 文件对应的业务类型：模板 "TEMPLATE"、流程 "FLOW"、文档 "DOCUMENT"
            req.setBusinessType(null);
            // 业务编号的数组，如模板id、流程id、文档id
            req.setBusinessIds(null);
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

    // 用户信息
    private UserInfo getUserInfo() {
        UserInfo userInfo = new UserInfo();
        // 用户在平台的编号
        userInfo.setUserId("1");
//        // 用户的来源渠道
//        userInfo.setChannel("1");
//        // 用户在渠道的编号
//        userInfo.setOpenId("1");
//        // 用户的真实ip
//        userInfo.setClientIp("1");
//        // 用户代理ip
//        userInfo.setProxyIp("1");
        return userInfo;
    }


}
