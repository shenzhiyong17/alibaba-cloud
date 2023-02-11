package szy.cloud.order.api;

import org.springframework.cloud.openfeign.FeignClient;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import szy.cloud.order.module.Order;

@FeignClient(value="order-service",
        fallback = OrderFallBack.class)  // OrderFallBack为相应fallback类，必须先设属性feign.hystrix.enable=true
public interface OrderApi {

    @RequestMapping(value = "/getOrder", method = RequestMethod.GET)
    Order getOrder(@RequestParam("id") int id);


    @RequestMapping(value = "insert", method = RequestMethod.POST)
    Integer insert(@RequestBody Order order);

    @RequestMapping(value = "update", method = RequestMethod.POST)
    Integer update(@RequestBody Order order);

    @RequestMapping(value = "delete", method = RequestMethod.GET)
    Integer delete(@RequestParam("id") int id);
}