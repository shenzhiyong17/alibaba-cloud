package szy.alibaba.cloud.sharding.module;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

@Data
@TableName("t_order")
public class Order {

    @TableId(type = IdType.ASSIGN_ID) // 分布式Id
    Long id;
    String orderNo;
    Long userId;
    BigDecimal amount;
}
