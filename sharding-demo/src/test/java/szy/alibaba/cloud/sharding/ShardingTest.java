package szy.alibaba.cloud.sharding;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import szy.alibaba.cloud.sharding.mapper.OrderItemMapper;
import szy.alibaba.cloud.sharding.mapper.OrderMapper;
import szy.alibaba.cloud.sharding.mapper.OrderVOMapper;
import szy.alibaba.cloud.sharding.module.Order;
import szy.alibaba.cloud.sharding.module.OrderItem;

import java.math.BigDecimal;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ShardingApplication.class)
public class ShardingTest {

    @Autowired
    OrderMapper orderMapper;

    @Autowired
    OrderItemMapper orderItemMapper;

    @Autowired
    OrderVOMapper orderVOMapper;

    @Test
    public void testInsert() {
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
    public void testQueryOne() {
        // by user_id
        System.out.println(orderMapper.selectOne(new QueryWrapper<Order>().eq("user_id", 10)));
        // by order_no
        System.out.println(orderMapper.selectOne(new QueryWrapper<Order>().eq("order_no", "Order52XX")));
        // by other key
        System.out.println(orderMapper.selectOne(new QueryWrapper<Order>().eq("amount", new BigDecimal(16))));
    }

    @Test
    public void testQueryMany() {
        // by user_id
        System.out.println(orderMapper.selectList(new QueryWrapper<Order>().between("user_id", 10, 20)));
        // by order_no
        orderMapper.selectList(new QueryWrapper<Order>().between("order_no", "Order5", "Order52XX")).forEach(System.out::println);
        // by other key
        System.out.println(orderMapper.selectList(new QueryWrapper<Order>().between("amount", 20, 50)));
    }

    /**
     * 测试关联表插入
     */
    @Test
    public void testInsertOrderAndItem() throws InterruptedException{
        Random random = new Random();
        for (int i = 15; i < 20; i++) {
            Order order = new Order();
            order.setUserId(1L);
            order.setOrderNo("UnionOrder" + i);
            orderMapper.insert(order);
            for (int j = 0; j < 3; j++) {
                Thread.sleep(random.nextInt(5));
                OrderItem orderItem = new OrderItem();
                orderItem.setUserId(1L);
                orderItem.setOrderNo("UnionOrder" + i);
                orderItem.setCount(random.nextInt(5));
                orderItem.setPrice(new BigDecimal(random.nextInt(10)));
                orderItemMapper.insert(orderItem);
            }
        }
        for (int i = 20; i < 25; i++) {
            Order order = new Order();
            order.setUserId(2L);
            order.setOrderNo("UnionOrder" + i);
            orderMapper.insert(order);
            for (int j = 0; j < 3; j++) {
                Thread.sleep(random.nextInt(5));
                OrderItem orderItem = new OrderItem();
                orderItem.setUserId(2L);
                orderItem.setOrderNo("UnionOrder" + i);
                orderItem.setCount(random.nextInt(4));
                orderItem.setPrice(new BigDecimal(random.nextInt(10)));
                orderItemMapper.insert(orderItem);
            }
        }
    }

    /**
     * 联合查询 order表和order_item表，组装到 VO
     */
    @Test
    public void testGetOrderVOList(){
        orderVOMapper.listVOList().forEach(System.out::println);
    }
}
