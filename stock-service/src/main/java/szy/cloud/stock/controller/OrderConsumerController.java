package szy.cloud.stock.controller;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import szy.cloud.order.api.OrderApi;
import szy.cloud.order.module.Order;

import javax.annotation.PostConstruct;

@RestController
@RequestMapping("/order")
@Slf4j
public class OrderConsumerController {

    @Autowired
    OrderApi orderApi;

    private Order initOrder;

    @PostConstruct
    private void init(){
        initOrder = new Order();
        initOrder.setPrice(102);
        initOrder.setId(800);
        initOrder.setUserName("王五");
        initOrder.setProductId(2400);
    }

    @RequestMapping(value = "insert", method = RequestMethod.POST)
    public int insertOrder(@RequestBody Order order){
        return orderApi.insert(order);
    }

    @RequestMapping(value = "update")
    public int update(@RequestBody Order order){
        return orderApi.update(order);
    }

    @RequestMapping(value = "get")
    public Order getOrder(@RequestParam("id") int id){
        log.info("get order {}", id);
        return orderApi.getOrder(id);
    }

    @RequestMapping(value = "delete")
    public int delete(@RequestParam("id") int orderId){
        return orderApi.delete(orderId);
    }
}
