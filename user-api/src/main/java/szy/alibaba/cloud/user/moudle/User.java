package szy.alibaba.cloud.user.moudle;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("user")
public class User {

    @TableId
    private Long id;

    @TableField("username")
    private String username;
    private String password;
    private String phone;
    private int age;
    private String nickname;
    private String gender;
    private long createTime = System.currentTimeMillis();
    private long updateTime = System.currentTimeMillis();
    private int delFlag = 0;

}
