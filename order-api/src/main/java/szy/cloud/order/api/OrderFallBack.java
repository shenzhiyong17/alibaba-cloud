package szy.cloud.order.api;

import org.springframework.stereotype.Component;
import szy.cloud.order.module.Order;

@Component
public class OrderFallBack implements OrderApi{
    @Override
    public Order getOrder(int id) {
        return new Order();
    }

    @Override
    public Integer insert(Order order) {
        return -1;
    }

    @Override
    public Integer update(Order order) {
        return -1;
    }

    @Override
    public Integer delete(int id) {
        return -1;
    }
}
