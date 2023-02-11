package szy.alibaba.cloud.stock.api;

import org.springframework.stereotype.Component;
import szy.alibaba.cloud.stock.module.Stock;

@Component
public class StockApiFallback implements StockApi{
    @Override
    public Stock getStock(int id) {
        return new Stock(0, -1, null, 0);
    }
}
