package com.example.live.common;

public class PayInfo {

    /**
     * 支付宝
     */

    // 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
    public static final String app_id = "2017061907523514";

    // 商户私钥，您的PKCS8格式RSA2私钥
    public static final String merchant_private_key = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCnCqfPLcBLWokuzk4OgQ9PIQeRGKygjwyDaL9OsotqEyDr/MbY/KlxcmPbtXnv67A82UtaQOf6nhkYF3IqmwQT3TJA5Ld/9v0rM/TRoPYsiYtFvCLy6s1h++c57uSNAE/NxkrQMe/fEMpVQ7Jr1M2tmjNErwLsUoV0DykviVl2OITtt7XqrN0q+ishV00CkHv1rLZY5kWeG0gdv5DqZ0xkSBSWrjoOcn0CFT7+Ab+TFZ7OOcFS7DPXXZCmmyeSCpExJdcbz0ppggOuoPD36odzcuJFNAjvRDoKD/rHZLp1TKA0UfIH0/igBOw8nMhAuJj9LTDiG4c+ZVQ0RIsHb/ZjAgMBAAECggEAUUlqJ/PHo+PG5jQW4BAF9yfHmuA7AVcba8dJcW6JUXFFkBFOYsFxY3ebCIvjbtFuze2gMadsPBU7ePHP6NPOxCvcDKBAe9EhmiiKdoixwzKbx6+qAjQpBgmjmBmcttD0BNr+BRqH9DEjhSKQbND91gE3+bCA56ewsa2/CRb5pgFHOoPm+SXXnxeJ3OKclzYjABWvyTXP030JqdQhmSOvBVPO/g0z1ZLQEj/J4CUF2p+00pkW+9ygS7wMpFq0IMQE9axuTO4pL3UcnuI4EMrGDFd4ljsFO/KFKJtLGSp84Qevmwspqy8sFez262rkxEZbeYnDdTGr+vm4jYMf03usQQKBgQDyjX8Yr8f3787helMWkq/X6bOXpMkI7paEK2D6CnfJ3arwcx99eoArZg1D9myqJZZnz0kiQFQpe6Z9ldjyRs7zXfbXg8fJNW3j81okcYOJMfoTqAQPk0yWxXcUcQok1J/2/GobWog2WbRcdJVsu9bXHyKk99ObImAmQfVhSYtSyQKBgQCwTXHZ+HzLP3p1ksCUoNpcpuwuadWw+MA4ipSyUTQfyNSnakI1Du61nlgXB1WW5Dic6+Jiefxo8lUsTKVe+YxKfXmmi+N+hoOzLFbkxlbiVB1a4b3t79xFxoELrPgwIw/e6eyd9GjCM56Dk73kXyEkJuKYgo/V6tlCTzOKA4pJywKBgCnr2L6sivRIR0pjOP8axTedFcQs3+4iRnN7h9xPeNqz7Dp4u0WVqZdWoQ5RBgD2VCOHCQ3ZbnJpA6KLBp8BK/2WDPa8uQgGqwKN6dOIpwJMV/uTu3fxrngUkvs+elFZSniGsdQedpJmOK0OHVFYuc4efH9ksfcLHvEdz0pMSr85AoGAQO3EqEnja9cM+o/5bMNwp/6xrMY9dNensysWaDWPxMTpzF5N08yUDT8rog+JbfDPCLu2vB3A38WfqJjxrnCpQiXDz5plRgu0JSCHP+nrxZ7E0NuiXyafMPNSQ0KIhPHkvmvYQLPqgNsR3dNjc9+1DONlQFWVM2syaEFS8dkr7NECgYBigYU5ehxIOu37yjDSzcCvbIvQ4C7b+bs122kXraZIsOFTrqrUx+J1bfTRab/ZcbnZVzfS3pz+0Sry8iEX8vj62Nx2A/Ppgox7hTFTCcxhKWY4XWVcbYG/x8Tcgz7JjtzXsTOf1ohCVEOGYj9yuXpKCkgU4p2276stoDP565RlLg==";

    // 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    public static final String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAi+zEH9bFKyyZbD3HvagDYZ5j3gtRcclJm6/UL70Sc6QcCgCcRXZ+1FLbYHt21/loS9fWKdPwQhmFzshj/qF4zMLE2deQMx3/5OGUrRt9jCjXl575RI/v66w5y+zYdw7b55KyA+DAEv9wjhg3eWYUmByx+weLif8jWtSRtJ0W2L8Ot2HLB5ki3HdgECM97uT/ycV+9rIysbabkk08XN6iUxyOP4q1+lO9lMwGfjcBSYamF5pXDd54sPCUj5EUadbwJztOM6fPtJkVKCTcjH+ITJO+6oOx7+4Yt23OXMSMTI70MvJGu5HepddUvpfaXXZ/cYIRi5KKBQuYlU/VqaiAvwIDAQAB";

    // 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static final String notify_url = "";

    // 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static final String return_url = "";

    // 签名方式
    public static final String sign_type = "RSA2";

    // 字符编码格式
    public static final String charset = "utf-8";

    // 支付宝网关
    public static String gatewayUrl = "https://openapi.alipay.com/gateway.do";


    /**
     * 微信
     */

    // 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static final String notify_url2 = "";

    // 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static final String return_url2 = "";

}
