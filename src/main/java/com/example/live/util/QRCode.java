package com.example.live.util;

import com.github.binarywang.utils.qrcode.MatrixToImageWriter;
import com.google.common.collect.Maps;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.apache.commons.lang3.StringUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;

/**
 * 二维码
 * Created by htuser8 on 2020/5/12.
 */
public class QRCode {

    public static void qrCode(String s, String path) {
        HashMap map = Maps.newHashMap();
        map.put(EncodeHintType.CHARACTER_SET, "utf-8");
        // 纠错等级：M级：约可纠错15%的数据码字
        map.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
        map.put(EncodeHintType.MARGIN, 2);
        try {
            BitMatrix bitMatrix = new MultiFormatWriter().encode(s, BarcodeFormat.QR_CODE, 300, 300, map);
            File file = new File(path);
            MatrixToImageWriter.writeToFile(bitMatrix, "png", file);
//            Path file = new File(path).toPath();
//            MatrixToImageWriter.writeToPath(bitMatrix,"png", file);
        } catch (WriterException | IOException e) {
            e.printStackTrace();
        }
    }

    private static final String IMAGE = "data:image/png;base64,";
    /**
     * 生成二维码：需要在字符串前面拼接{data:image/png;base64,}即可显示
     * @param args
     * @return
     */
    public static String code(String args) {
        if (StringUtils.isBlank(args)) {
            return args;
        }
        String result = "";
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        HashMap map = Maps.newHashMap();
        map.put(EncodeHintType.CHARACTER_SET, "utf-8");
        // 纠错等级：M级：约可纠错15%的数据码字
        map.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
        map.put(EncodeHintType.MARGIN, 2);
        BitMatrix bitMatrix;
        try {
            bitMatrix = qrCodeWriter.encode(args, BarcodeFormat.QR_CODE, 300, 300, map);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(bitMatrix, "png", outputStream);
            Base64.Encoder encoder = Base64.getEncoder();
            result = encoder.encodeToString(outputStream.toByteArray());
        } catch (WriterException | IOException e) {
            e.printStackTrace();
        }
        return IMAGE + result;
    }

}
