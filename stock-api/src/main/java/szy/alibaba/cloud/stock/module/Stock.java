package szy.alibaba.cloud.stock.module;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Stock {

    int id;
    int productId;
    String productName;
    int stockCount;

}
