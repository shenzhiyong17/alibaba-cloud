package szy.cloud.order.module;

import lombok.Data;


@Data
public class Order {

    int id;
    int productId;
    int price;
    int status = 1;
    int userId;
    String userName;
    long payTime;
    long createTime ;
    long updateTime = System.currentTimeMillis();

}
