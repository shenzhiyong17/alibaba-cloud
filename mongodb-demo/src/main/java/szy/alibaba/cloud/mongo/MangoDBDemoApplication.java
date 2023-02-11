package szy.alibaba.cloud.mongo;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MangoDBDemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(MangoDBDemoApplication.class, args);
        System.out.println("Hello MangoDBDemoApplication!");
    }
}