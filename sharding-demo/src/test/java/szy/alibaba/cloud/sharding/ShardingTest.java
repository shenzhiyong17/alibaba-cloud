package szy.alibaba.cloud.sharding;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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
        for (int i = 0; i < 40; i++) {
            Order order = new Order();
            order.setAmount(new BigDecimal(random.nextInt(100)));
            order.setUserId((long) i);
            order.setOrderNo("Order" + random.nextInt(100) + "XX");
            System.out.println(orderMapper.insert(order));
        }
    }

    @Test
    public void testQueryOne(){
        // by user_id
        System.out.println(orderMapper.selectOne(new QueryWrapper<Order>().eq("user_id", 10)));
        // by order_no
        System.out.println(orderMapper.selectOne(new QueryWrapper<Order>().eq("order_no", "Order52XX")));
        // by other key
        System.out.println(orderMapper.selectOne(new QueryWrapper<Order>().eq("amount", new BigDecimal(16))));
    }

    @Test
    public void testQueryMany(){
        // by user_id
        System.out.println(orderMapper.selectList(new QueryWrapper<Order>().between("user_id", 10, 20)));
        // by order_no
        orderMapper.selectList(new QueryWrapper<Order>().between("order_no", "Order5", "Order52XX")).forEach(System.out::println);
        // by other key
        System.out.println(orderMapper.selectList(new QueryWrapper<Order>().between("amount", 20, 50)));
    }

}
