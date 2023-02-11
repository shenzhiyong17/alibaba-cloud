package szy.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import java.io.IOException;

@SpringBootApplication
@EnableDiscoveryClient
public class GatewayApplication {
    public static void main(String[] args) throws IOException {
        SpringApplication.run(GatewayApplication.class, args);
        System.out.println("gateway nacos server");
        System.in.read();
    }
}