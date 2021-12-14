package com.olivine.consumer;

import com.olivine.feign.client.user.UserClient;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;


/**
 * @Author jphao
 * @Date 2021/12/15 1:09
 * @Description
 */
@EnableFeignClients(basePackages = {"com.olivine.feign.client"})
@SpringBootApplication
public class FeignConsumerApplication {
}
