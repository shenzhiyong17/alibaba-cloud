package szy.alibaba.cloud.sharding;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("szy.alibaba.cloud.sharding")
public class ShardingApplication {
    public static void main(String[] args) {
        SpringApplication.run(ShardingApplication.class, args);
        System.out.println("Hello ShardingApplication!");
    }
}