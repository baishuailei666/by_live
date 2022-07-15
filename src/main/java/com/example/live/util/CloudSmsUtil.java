package com.example.live.util;

import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.sms.v20190711.SmsClient;
import com.tencentcloudapi.sms.v20190711.models.SendSmsRequest;
import com.tencentcloudapi.sms.v20190711.models.SendSmsResponse;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 腾讯云短信工具类
 *
 * @author baishuailei@zhejianglab.com
 * @date 2022/7/15 15:57
 */
@Component
public class CloudSmsUtil {

    private static final String sms_secretId = "";
    private static final String sms_secretKey = "";
    private static final String sms_sdkAppId = "";
    private static final String sms_templateId = "";

    private static final SmsClient smsClient = getSmsClient();

    private static SmsClient getSmsClient() {
        if (smsClient != null) {
            return smsClient;
        }
        return smsClientInit();
    }

    private static SmsClient smsClientInit() {
        // 实例化一个认证对象，入参需要传入腾讯云账户secretId，secretKey,此处还需注意密钥对的保密
        // 密钥可前往https://console.cloud.tencent.com/cam/capi网站进行获取
        Credential cred = new Credential(sms_secretId, sms_secretKey);
        // 实例化一个http选项，可选的，没有特殊需求可以跳过
        HttpProfile httpProfile = new HttpProfile();
        httpProfile.setEndpoint("sms.tencentcloudapi.com");
        // 实例化一个client选项，可选的，没有特殊需求可以跳过
        ClientProfile clientProfile = new ClientProfile();
        clientProfile.setHttpProfile(httpProfile);
        // 实例化要请求产品的client对象,clientProfile是可选的
        return new SmsClient(cred, "", clientProfile);
    }

    /**
     * 发送短信
     */
    public void sendSms(List<String> mobile) {
        try {
            // 实例化一个请求对象,每个接口都会对应一个request对象
            SendSmsRequest req = new SendSmsRequest();
            req.setPhoneNumberSet(mobile.toArray(new String[1]));
            req.setSmsSdkAppid(sms_sdkAppId);
            req.setTemplateID(sms_templateId);

            // 返回的resp是一个SendSmsResponse的实例，与请求对象对应
            SendSmsResponse resp = smsClient.SendSms(req);
            // 输出json格式的字符串回包
            System.out.println("#send sms:" + SendSmsResponse.toJsonString(resp));
        } catch (TencentCloudSDKException e) {
            e.printStackTrace();
        }
    }
}
