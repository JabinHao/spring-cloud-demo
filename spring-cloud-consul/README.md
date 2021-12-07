# spring-cloud-consul

## 服务部署


## spring 应用

### 服务注册

1. 依赖

    ```xml
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-consul-discovery</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-actuator</artifactId>
    </dependency>
    ```

2. application.yml
    ```yaml
    spring
      cloud:
        consul:
          host: 114.214.219.193
          port: 8500
          discovery:
            instance-id: ${spring.application.name}-02
            service-name: ${spring.application.name}
            port: ${server.port}
            prefer-ip-address: true
            ip-address: 114.214.219.193
            health-check-interval: 10s
    ```

3. 启动类
    ```java
    @SpringBootApplication
    @EnableDiscoveryClient
    public class ConsulOrderApplication {

        public static void main(String[] args) {
            SpringApplication.run(ConsulOrderApplication.class, args);
        }

    }
    ```

### 服务发现(Ribbon)

1. application.yml
    ```yaml
    spring
      cloud:
        consul:
          host: 114.214.219.193
          port: 8500
          discovery:
            register: false
    ```
2. restTemplate

    ```java
    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
    ```


