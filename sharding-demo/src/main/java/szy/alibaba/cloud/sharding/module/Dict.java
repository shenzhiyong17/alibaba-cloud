package szy.alibaba.cloud.sharding.module;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 字典表， 配置表
 */
@Data
@TableName("t_dict")
public class Dict {

    @TableId(type = IdType.ASSIGN_ID)
    Long id;

    String config;
}
