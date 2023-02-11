package szy.cloud.stock.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.netflix.hystrix.contrib.javanica.conf.HystrixPropertiesManager;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import szy.alibaba.cloud.stock.api.StockApi;
import szy.alibaba.cloud.stock.module.Stock;
import szy.cloud.common.apo.LOG;
import szy.cloud.order.api.OrderApi;
import szy.cloud.order.module.Order;
import szy.cloud.stock.mapper.StockMapper;

@RestController
@Slf4j
@LOG
public class StockController implements StockApi {

    @Autowired
    OrderApi orderApi;

    @Autowired
    StockMapper stockMapper;

    @RequestMapping(value = "/getStock", method = RequestMethod.GET)
    public Stock getStock(@RequestParam("id") int id) {
        log.info("get id = {}", id);
        Stock stock = stockMapper.selectById(id);
        if (stock != null){
            return stock;
        }
        throw new RuntimeException("库存不存在");
    }

    @RequestMapping(value = "getOrderFromStock", method = RequestMethod.GET)
    @HystrixCommand(
            fallbackMethod = "getOrderFallBack",
            commandProperties = { // 熔断默认开启，在此配置熔断条件，各属性都有默认值
                    @HystrixProperty(name = HystrixPropertiesManager.CIRCUIT_BREAKER_REQUEST_VOLUME_THRESHOLD, value = "5") //
            })
    public Order getOrderFromStock(@RequestParam("id") int id) {
        log.info("getOrderFromStock {}", id);
        return orderApi.getOrder(id);
    }

    public Order getOrderFallBack(int id) {
        log.info("getOrderFallBack: {}", id);
        return new Order();
    }


}
