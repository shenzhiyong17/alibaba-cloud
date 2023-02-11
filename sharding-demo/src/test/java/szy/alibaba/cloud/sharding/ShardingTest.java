package szy.alibaba.cloud.sharding;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import szy.alibaba.cloud.sharding.mapper.OrderMapper;
import szy.alibaba.cloud.sharding.module.Order;

import java.math.BigDecimal;
import java.util.Random;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ShardingApplication.class)
public class ShardingTest {

    @Autowired
    OrderMapper orderMapper;

    @Test
    public void testInsert(){
        Random random = new Random();
        for (int i = 10; i < 20; i++) {
            Order order = new Order();
            order.setAmount(new BigDecimal(10));
            order.setUserId(15L + i);
            order.setOrderNo("OrderXX0" + random.nextInt(100));
            System.out.println(orderMapper.insert(order));
        }
    }

    @Test
    public void testHash(){
        for (int i = 50; i < 60; i++) {
            System.out.println(( i + "Order00100").hashCode() % 2);
        }
    }
}
