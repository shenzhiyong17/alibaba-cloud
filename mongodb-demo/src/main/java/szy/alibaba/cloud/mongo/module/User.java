package szy.alibaba.cloud.mongo.module;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

@Document("users")  // 加上这个注解表示这个类的实例可以转化为mongo中的一条文档,里面的参数表示这个文档日后放入哪个集合
@Data
public class User {
    @Id     // 将这个类的id映射为文档的_id
    private Integer id;
    @Field("username")   // 里面参数表示将这个字段映射为文档中字段的名字
    private String name;
    @Field               // 不写参数表明映射的对应文档中的字段名为属性名
    private Double salary;
    @Field
    private Date birthday;

    // get、set、toString、构造方法······
}
