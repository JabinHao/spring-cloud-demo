package com.olivine.feign.config;

import com.olivine.feign.client.order.fallback.OrderClientFallbackFactory;
import feign.Logger;
import org.springframework.context.annotation.Bean;

/**
 * @Author jphao
 * @Date 19:37 2021/12/24
 * @Description
 */
public class DefaultFeignConfig {

    @Bean
    public Logger.Level logLevel(){
        return Logger.Level.BASIC;
    }

    @Bean
    public OrderClientFallbackFactory orderClientFallbackFactory(){
        return new OrderClientFallbackFactory();
    }
}
