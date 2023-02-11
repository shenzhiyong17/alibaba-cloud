package szy.alibaba.cloud.stock.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import szy.alibaba.cloud.stock.module.Stock;

@FeignClient(value = "stock-service", fallback = StockApiFallback.class)
public interface StockApi {

    @RequestMapping(value = "/getStock", method = RequestMethod.GET)
    public Stock getStock(@RequestParam("id") int id) ;


}
