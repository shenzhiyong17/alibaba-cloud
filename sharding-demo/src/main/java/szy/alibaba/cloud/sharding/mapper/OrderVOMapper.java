package szy.alibaba.cloud.sharding.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import szy.alibaba.cloud.sharding.module.OrderVO;

import java.util.List;

@Mapper
public interface OrderVOMapper extends BaseMapper<OrderVO> {


    // @Select 中的数组会自动串起来组成一个字符串，海会自动添加空格
    @Select({"SELECT o.order_no, SUM(i.price * i.count) AS amount FROM",
            "t_order o JOIN t_order_item i ON o.`order_no` = i.order_no",
            "GROUP BY order_no"})
    List<OrderVO> listVOList();
}
