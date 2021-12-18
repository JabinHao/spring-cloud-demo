## config 热更新
### 方式一: 注解
   * `@Value`
   * `@RefreshScope`

```java
@RefreshScope
@RestController
@RequestMapping("order")
public class TestController {

   @Value("${user.age}")
   private String age;

   @Value("${user.name}")
   private String name;
}
```

### 方式二: 配置类
1. 使用注解 `@ConfigurationProperties`
2. 无需 `@RefreshScope` 注解，自动实现热更新

```java
@Data
@Component
@ConfigurationProperties(prefix = "pattern")
public class OrderConfig {

    private String dateformat;
}
```

## 其它
### 1. 多环境
1. 优先级：serviceName-profile > serviceName > 本地配置
## 问题
1. ~~Spring Cloud 版本比 Spring Cloud Alibaba 领先, 部分配置有问题~~ 
   * ~~Nacos config 功能：不支持环境配置 (profiles.active)~~