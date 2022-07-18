package com.example.live.util;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.http.HttpProtocol;
import com.qcloud.cos.model.*;
import com.qcloud.cos.region.Region;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * @author baishuailei@zhejianglab.com
 * @date 2022/7/18 16:02
 */
@Component
public class CloudCosUtil {

    private static final String secretId = "";

    private static final String secretKey = "";

    // 存储桶名称，格式：BucketName-APPID
    private static final String bucket = "live-0710";

    private static final String cos_region = "ap-shanghai";

    // 视频文件
    private static final String video_key = "";

    private static final COSClient cosClient = getCosClient();

    private static COSClient getCosClient() {
        if (cosClient != null) {
            return cosClient;
        }
        return cosClient();
    }

    private static COSClient cosClient() {
        // 1、初始化用户身份信息
        COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
        // 2、设置bucket的地域
        // clientConfig 中包含了设置 region, https(默认 http), 超时, 代理等 set 方法
        Region region = new Region(cos_region);
        ClientConfig clientConfig = new ClientConfig(region);
        // 这里建议设置使用https协议
        clientConfig.setHttpProtocol(HttpProtocol.https);
        // 3 生成 cos 客户端。
        return new COSClient(cred, clientConfig);
    }

    // 视频上传
    public String uploadVideo(String filepath) {
        // 指定要上传的文件
        File file = new File(filepath);
        String key = video_key+filepath;
        // 指定文件要存储的存储桶
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucket, key, file);
        PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);
        System.out.println(putObjectResult.getRequestId());
        return key;
    }


}
