## 项目结构
### 模块
1. service: order、user
2. gateway
3. feign client

### 版本
1. sentinel 版本开发滞后于 Spring Cloud, 因此采用旧版本保证兼容性  

    |Spring Cloud Alibaba Version |Spring Cloud Version |Spring Boot Version  
    :--- | :--- |:---   
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

### 1. 流量控制
1. 

### 2. 线程隔离

1. 线程池隔离
   * 线程开销大
   * 支持主动超时，异步调用
   * 适合低扇出场景

2. 信号量隔离
    * 轻量
    * 不支持主动超时，异步调用
    * 适合高频调用，高扇出场景

### 3. 熔断降级
1. 思路
   * 由断路器统计服务调用的异常比例、慢请求比例，如果超出阈值则会熔断该服务。
   * 熔断即拦截访问该服务的一切请求；而当服务恢复时，断路器会放行访问该服务的请求
     ![](https://raw.githubusercontent.com/JabinHao/mihs/master/blog/SpringCloudsentinel-1.png)   

2. 熔断策略
   * 慢调用
      * 业务的响应时长（RT）大于指定时长的请求认定为慢调用请求。
      * 在指定时间内，如果请求数量超过设定的最小数量，慢调用比例大于设定的阈值，则触发熔断
   * 异常比例与异常数
      * 统计指定时间内的调用，如果调用次数超过指定请求数，并且出现异常的比例达到设定的比例阈值（或超过指定异常数），则触发熔断

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
   
### 授权规则
1. 说明
   * 对调用方的来源做控制，有白名单和黑名单两种方式
   * Sentinel通过 `RequestOriginParser` 接口的 `parseOrigin` 方法来获取请求的来源
       ```java
       public interface RequestOriginParser {
    
           /**
            * Parse the origin from given HTTP request.
            *
            * @param request HTTP request
            * @return parsed origin
            */
           String parseOrigin(HttpServletRequest request);
       }
       ```

2. 示例
    ```java
    package com.olivine.user.sentinel;
    
    import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.RequestOriginParser;
    import org.springframework.stereotype.Component;
    
    import javax.servlet.http.HttpServletRequest;
    import java.util.Set;
    import java.util.stream.Collectors;
    import java.util.stream.Stream;
    
    /**
     * @Author jphao
     * @Date 12:47 2021/12/28
     * @Description
     */
    @Component
    public class HeaderOriginParser implements RequestOriginParser {
    
        private final Set<String> applications = Stream.of("order", "user").collect(Collectors.toSet());
    
        @Override
        public String parseOrigin(HttpServletRequest httpServletRequest) {
            String origin = httpServletRequest.getHeader("origin");
            return (origin == null || !applications.contains(origin)) ? "blank" : origin;
        }
    }
    ```
3. 自定义异常结果
   * 默认情况下，发生限流、降级、授权拦截时，都会抛出异常到调用方
   * 可以实现 `BlockExceptionHandler` 接口，自定义异常返回结果
     ```java
     public interface BlockExceptionHandler {

         /**
          * Handle the request when blocked.
          *
          * @param request  Servlet request
          * @param response Servlet response
          * @param e        the block exception
          * @throws Exception users may throw out the BlockException or other error occurs
          */
         void handle(HttpServletRequest request, HttpServletResponse response, BlockException e) throws Exception;

     }
     ```

   * `BlockException` 子类
     * FlowException  限流异常
     * ParamFlowException  热点参数限流的异常
     * DegradeException  降级异常
     * AuthorityException  授权规则异常
     * SystemBlockException  系统规则异常
   
### 规则持久化

1. 三种模式

    推送模式 | 说明 | 优点 | 缺点
    :--- |:--- |:--- |:---
    原始模式 | API 将规则推送至客户端并直接更新到内存中，扩展写数据源（WritableDataSource）	简单，无任何依赖 | 不保证一致性；规则保存在内存中，重启即消失。严重不建议用于生产环境
    Pull 模式 | 扩展写数据源（WritableDataSource）， 客户端主动向某个规则管理中心定期轮询拉取规则，这个规则中心可以是 RDBMS、文件 等 | 简单，无任何依赖；规则持久化	不保证一致性；实时性不保证，拉取过于频繁也可能会有性能问题。
    Push 模式 | 扩展读数据源（ReadableDataSource），规则中心统一推送，客户端通过注册监听器的方式时刻监听变化，比如使用 Nacos、Zookeeper 等配置中心。这种方式有更好的实时性和一致性保证。生产环境下一般采用 push 模式的数据源。	规则持久化；一致性；快速 | 引入第三方依赖

2. 规则持久化实现：nacos（push模式）
   * 依赖
      ```xml
      <dependency>
          <groupId>com.alibaba.csp</groupId>
          <artifactId>sentinel-datasource-nacos</artifactId>
      </dependency>
      ```

   * 修改微服务中的sentinel配置
      ```yaml
      spring:
        cloud:
          datasource:
            flow: # 限流
              nacos:
                server-addr: localhost:8848
                dataId: order-service-flow-rules
                groupId: SENTINEL_GROUP
                rule-type: flow      
            degrade: # 降级           
              nacos:            
                server-addr: loc
                dataId: order-se
                groupId: SENTINE
                rule-type: degrade    
      ```

3. sentinel-dashboard 设置：
   * 整合nacos需要修改sentinel源码并重新打包
   * release 有打包好的 sentinel-1.8.3
      

