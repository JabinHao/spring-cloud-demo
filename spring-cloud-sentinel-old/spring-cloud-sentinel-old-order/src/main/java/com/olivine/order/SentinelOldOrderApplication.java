package com.olivine.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author jphao
 */
@EnableDiscoveryClient
@SpringBootApplication
public class SentinelOldOrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(SentinelOldOrderApplication.class, args);
    }

}
