package szy.cloud.stock;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.EnableScheduling;
import szy.cloud.order.api.OrderApi;

import java.io.IOException;

@SpringBootApplication
@ComponentScan("szy.cloud")
@EnableFeignClients(basePackages = {"szy.cloud"}) // 生成代理对象
@EnableDiscoveryClient  // @EnableEurekaClient只适用于Eureka作为注册中心，@EnableDiscoveryClient 可以是其他注册中心。
@EnableHystrix // 激活hystrix的功能
@EnableHystrixDashboard
@EnableCircuitBreaker // @EnableHystrix注解已经涵盖了EnableCircuitBreaker的功能。
@EnableScheduling
@ImportResource("classpath*:spring-application.xml")
@MapperScan("szy.cloud.stock.mapper")

public class StockServiceApplication {
    public static void main(String[] args) throws IOException {
        SpringApplication.run(StockServiceApplication.class, args);
        System.out.println("StockServiceApplication");
        System.in.read();
    }
}