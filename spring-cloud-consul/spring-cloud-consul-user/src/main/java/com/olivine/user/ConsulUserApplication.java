package com.olivine.user;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * @Author jphao
 * @Date 2021/11/26 18:24
 * @Description
 */
@EnableDiscoveryClient
@MapperScan("com.olivine.user.mapper")
@SpringBootApplication
public class ConsulUserApplication {
    public static void main(String[] args) {
        SpringApplication.run(ConsulUserApplication.class, args);
    }

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
