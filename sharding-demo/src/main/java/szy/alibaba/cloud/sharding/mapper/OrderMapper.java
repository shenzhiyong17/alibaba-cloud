package szy.alibaba.cloud.sharding.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import szy.alibaba.cloud.sharding.module.Order;


@Mapper
@Component
public interface OrderMapper extends BaseMapper<Order> {
}
