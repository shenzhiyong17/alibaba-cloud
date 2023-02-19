package szy.alibaba.cloud.sharding.module;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderVO {
    String orderNo;

    BigDecimal amount;
}
