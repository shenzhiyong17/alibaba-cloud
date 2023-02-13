package szy.alibaba.cloud.sharding.module;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

@Data
@TableName("t_order")
public class Order {

    // IdType.AUTO: 当配置了shardingSphere的分布式序列时，使用shardingSphere的分布式序列，没有配置shardingSphere的分布式序列是使用主键自增
    // 如果不配，默认为ASSIGN_ID，优先级高于shardingSphere的分布式序列，所以如果想用shardingSphere的snowflake，此处需配置为AUTO
    @TableId(type = IdType.ASSIGN_ID) // 分布式Id，雪花算法
    Long id;
    String orderNo;
    Long userId;
    BigDecimal amount;
}
