package com.olivine.order.controller;

import com.olivine.order.config.OrderConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @Author jphao
 * @Date 2021/12/17 12:07
 * @Description
 */
@RefreshScope
@RestController
@RequestMapping("order")
public class TestController {

    @Value("${user.age}")
    private String age;

    @Value("${user.name}")
    private String name;

    @Value("${user.env}")
    private String env;

    @Autowired
    private OrderConfig orderConfig;

    @GetMapping("/now")
    public String now(){
        System.out.println("name: " + name + ", age: " + age);
        System.out.println("env: " + env);
        System.out.println(orderConfig);
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(orderConfig.getDateformat()));
    }
}
