package szy.alibaba.cloud.user;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import szy.alibaba.cloud.stock.api.StockApi;

@SpringBootApplication
@MapperScan("szy.alibaba.cloud.user.mapper")
@ComponentScan({"szy.cloud.stock", "szy.alibaba"})
@EnableFeignClients(basePackageClasses = StockApi.class)
public class UserApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
        System.out.println("User Service start!");

    }
}