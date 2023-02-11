package szy.cloud.order.mapper;

import szy.cloud.order.module.Order;

public interface OrderMapper {

    int insert(Order order);

    int delete(int id);

    int updateStatus(Order order);

    Order getById(int id);

    int update(Order order);

}
