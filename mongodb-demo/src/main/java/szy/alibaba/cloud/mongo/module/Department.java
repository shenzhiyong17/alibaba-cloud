package szy.alibaba.cloud.mongo.module;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document
@Data
@Accessors(chain = true)
public class Department {

    @Id
    private String id;

    @Field("depno")
    private int departNo;

    @Field("depname")
    private String departName;

    private String loc;

}
