package szy.cloud.stock;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import szy.alibaba.cloud.stock.module.Stock;
import szy.cloud.stock.mapper.StockMapper;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = StockServiceApplication.class)
public class StockTest {

    @Autowired
    StockMapper stockMapper;

    @Test
    public void insertStock(){
        Stock stock = new Stock();
        stock.setStockCount(10);
        stock.setProductId(123);
        stock.setProductName("金箍棒");
        System.out.println(stockMapper.insert(stock));
    }
}
