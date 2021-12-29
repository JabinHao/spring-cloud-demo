package com.olivine.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @Author jphao
 * @Date 2021/12/15 17:21
 * @Description
 */
@SpringBootApplication
@EnableDiscoveryClient
public class SentinelOldGatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(SentinelOldGatewayApplication.class, args);
    }
}
