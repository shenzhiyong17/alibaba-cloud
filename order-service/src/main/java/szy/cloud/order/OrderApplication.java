package szy.cloud.order;

import org.apache.ibatis.io.Resources;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;

import java.io.IOException;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"szy.cloud.order"})
@ComponentScan(basePackages = {"szy.cloud"})
@ImportResource("classpath*:spring-application.xml")
public class OrderApplication {
    public static void main(String[] args) throws IOException {
        SpringApplication.run(OrderApplication.class, args);
        System.out.println("Hello OrderApplication!");
        System.in.read();
    }
}