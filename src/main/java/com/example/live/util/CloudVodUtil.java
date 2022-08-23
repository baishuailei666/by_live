package com.example.live.util;

import com.example.live.common.Constant;
import com.qcloud.vod.VodUploadClient;
import com.qcloud.vod.model.VodUploadRequest;
import com.qcloud.vod.model.VodUploadResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * 视频云点播
 *
 * @author baishuailei@zhejianglab.com
 * @date 2022/8/15 11:20
 */
@Component
public class CloudVodUtil {

    private static final String cos_region = "ap-shanghai";

    private static final VodUploadClient vodClient = getVodClient();

    private static VodUploadClient getVodClient() {
        if (vodClient != null) {
            return vodClient;
        }
        return vodClient();
    }

    private static VodUploadClient vodClient() {
        return new VodUploadClient(Constant.cloud_secretId, Constant.cloud_secretKey);
    }

    public static String videoUpload(MultipartFile file) {
        String url = null;
        try {
            File localFile = File.createTempFile("temp", null);
            file.transferTo(localFile);

            String[] split = file.getOriginalFilename().split("\\.");
            String format = split[split.length - 1];

            VodUploadRequest request = new VodUploadRequest();
            // 太博甄选课程学习视频
            request.setClassId(909525L);
            request.setMediaType(format);
            request.setStorageRegion(cos_region);
            request.setConcurrentUploadNumber(7);
            request.setMediaName(file.getResource().getFilename());
            request.setMediaFilePath(localFile.getCanonicalPath());
            try {
                VodUploadResponse response = vodClient.upload(cos_region, request);
                url = response.getMediaUrl();

                localFile.deleteOnExit();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return url;
    }

}
