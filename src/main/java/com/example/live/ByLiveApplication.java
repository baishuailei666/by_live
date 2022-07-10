package com.example.live;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

// 配置spring容器启动时扫描的包
@SpringBootApplication(scanBasePackages = {"com.example.live"})
@MapperScan("com.example.live.mapper")
public class ByLiveApplication {

    public static void main(String[] args) {
        SpringApplication.run(ByLiveApplication.class, args);
        System.out.println("***Application Start***");
    }

}
