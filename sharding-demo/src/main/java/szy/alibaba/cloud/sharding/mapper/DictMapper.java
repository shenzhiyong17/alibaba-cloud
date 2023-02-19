package szy.alibaba.cloud.sharding.mapper;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import szy.alibaba.cloud.sharding.module.Dict;

@Mapper
public interface DictMapper extends BaseMapper<Dict> {
}
