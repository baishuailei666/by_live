package com.example.live;

import com.example.live.util.QRCode;
import com.example.live.util.QRCodeUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ByLiveApplicationTests {

    @Test
    void contextLoads() {
        String base64QRCode = QRCodeUtil.getBase64QRCode("https://blog.csdn.net/su2231595742/article/details/119743585");
        System.out.println(base64QRCode);
        System.out.println("-------------");
        String code = QRCode.code("www.baidu.com");
        System.out.println(code);
    }

}
