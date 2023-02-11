package szy.alibaba.cloud.mongo.module;


import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

@Data
@Document("employee")
@Accessors(chain = true)
public class Employee {

    @Id
    private String id;

    private String name;

    private int empNo;

    private int salary;

    private String gender;

    private Date hireDate;

    @Field("depno")
    private int departNo;

    private int comm; // 提成

    private int delFlag = 0;

    private String job;

    private int mgr;




}
