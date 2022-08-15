package com.example.live.util;

import com.example.live.common.Constant;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.http.HttpMethodName;
import com.qcloud.cos.http.HttpProtocol;
import com.qcloud.cos.model.*;
import com.qcloud.cos.region.Region;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Date;

/**
 * @author baishuailei@zhejianglab.com
 * @date 2022/7/18 16:02
 */
@Component
public class CloudCosUtil {

    // 存储桶名称，格式：BucketName-APPID
    private static final String bucket = "taibo-culture-1313027383";

    private static final String cos_region = "ap-shanghai";

    // 视频文件
    private static final String video_key = "video/";

    private static final COSClient cosClient = getCosClient();

    private static COSClient getCosClient() {
        if (cosClient != null) {
            return cosClient;
        }
        return cosClient();
    }

    private static COSClient cosClient() {
        // 1、初始化用户身份信息
        COSCredentials cred = new BasicCOSCredentials(Constant.cloud_secretId, Constant.cloud_secretKey);
        // 2、设置bucket的地域
        // clientConfig 中包含了设置 region, https(默认 http), 超时, 代理等 set 方法
        Region region = new Region(cos_region);
        ClientConfig clientConfig = new ClientConfig(region);
        // 这里建议设置使用https协议
        clientConfig.setHttpProtocol(HttpProtocol.https);
        // 3 生成 cos 客户端。
        return new COSClient(cred, clientConfig);
    }

    // 简单文件上传, 最大支持 5 GB, 适用于小文件上传
    public String uploadVideo(MultipartFile file) {
        // 指定要上传的文件
        String key = null;
        try {
            File localFile = File.createTempFile("temp", null);
            file.transferTo(localFile);

            key = video_key+GeneralUtil.get4Random()+"-"+file.getOriginalFilename();
            System.out.println("key:"+key);
            // 指定文件要存储的存储桶
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucket, key, localFile);
            PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return key;
    }

    // 生成链接签名
    public String signExpire(String key) {
        // 设置签名过期时间(可选)，若未进行设置，则默认使用 ClientConfig 中的签名过期时间(1小时)
        // 这里设置签名在2个小时后过期
        Date expirationDate = new Date(System.currentTimeMillis() + 2L * 60L * 60L * 1000L);

        // 请求的 HTTP 方法，上传请求用 PUT，下载请求用 GET，删除请求用 DELETE
        HttpMethodName method = HttpMethodName.GET;

        URL url = cosClient.generatePresignedUrl(bucket, key, expirationDate, method);
        return url.toString();
    }


}
