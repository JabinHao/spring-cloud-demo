package com.olivine.order.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author jphao
 * @Date 2021/12/18 15:58
 * @Description
 */
@Data
@Component
@ConfigurationProperties(prefix = "pattern")
public class OrderConfig {

    private String dateformat;

    private String env;
}
