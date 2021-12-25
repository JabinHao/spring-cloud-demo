## 项目结构
### 模块

### 版本
1. sentinel 版本开发滞后于 Spring Cloud, 因此采用旧版本保证兼容性  

    |Spring Cloud Alibaba Version |Spring Cloud Version |Spring Boot Version  
    :- | :- |:-   
    |2.2.7.RELEASE|Spring Cloud Hoxton.SR12|2.3.12.RELEASE
    |2021.1|Spring Cloud 2020.0.1 |2.4.2
    |2.2.6.RELEASE|Spring Cloud Hoxton.SR9|2.3.2.RELEASE
    |2.1.4.RELEASE|Spring Cloud Greenwich.SR6|2.1.13.RELEASE
    |2.2.1.RELEASE|Spring Cloud Hoxton.SR3|2.2.5.RELEASE
    |2.2.0.RELEASE|Spring Cloud Hoxton.RELEASE|2.2.X.RELEASE
    |2.1.2.RELEASE|Spring Cloud Greenwich|2.1.X.RELEASE
    |2.0.4.RELEASE(停止维护，建议升级)|Spring Cloud Finchley|2.0.X.RELEASE
    |1.5.1.RELEASE(停止维护，建议升级)|Spring Cloud Edgware|1.5.X.RELEASE  
  

2. 新版本 Spring Cloud 已使用 LoadBalancer 替换 Ribbon, 需将 nacos discovery中的 ribbon 依赖排除

## Sentinel

### 熔断


### 整合 openfeign
1. 配置文件
    ```yaml
    feign:
      sentinel:
        enabled: true
    ```
2. 编写 FallbackFactory并注册为Bean
3. 在 `@FeignClient` 注解中配置
    ```java
    @FeignClient(value = "spring-cloud-sentinel-old-order-service", fallbackFactory = OrderClientFallbackFactory.class)
    ```