package com.olivine.user;

import com.olivine.feign.client.order.OrderClient;
import com.olivine.feign.config.DefaultFeignConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @Author jphao
 * @Date 2021/11/26 18:24
 * @Description
 */
@EnableFeignClients(clients = OrderClient.class, defaultConfiguration = DefaultFeignConfig.class)
//@EnableFeignClients
@MapperScan("com.olivine.user.mapper")
@SpringBootApplication
public class SentinelOldUserApplication {
    public static void main(String[] args) {
        SpringApplication.run(SentinelOldUserApplication.class, args);
    }
}
