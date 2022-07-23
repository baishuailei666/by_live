package com.example.live;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class ByLiveApplication {

    public static void main(String[] args) {
        SpringApplication.run(ByLiveApplication.class, args);
        System.out.println("***Application Start***");
    }

}
