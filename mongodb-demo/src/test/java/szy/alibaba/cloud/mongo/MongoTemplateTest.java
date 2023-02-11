package szy.alibaba.cloud.mongo;


import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.UpdateResult;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;

import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.test.context.junit4.SpringRunner;
import szy.alibaba.cloud.mongo.module.Department;
import szy.alibaba.cloud.mongo.module.Employee;
import szy.alibaba.cloud.mongo.module.User;

import java.util.*;


@RunWith(SpringRunner.class)
@SpringBootTest()
@Slf4j
/*
 * https://blog.csdn.net/qq_50313418/article/details/127092264
 * https://www.cnblogs.com/zaoyu/p/springboot-mongodb.html
 */
public class MongoTemplateTest {

    @Autowired
    MongoTemplate template;

    @Test
    public void testQuery() {
        //基于 id 查询
        User byId = template.findById("1", User.class);
        System.out.println(byId);

        //查询所有
        template.findAll(User.class);
        List<User> users = template.find(new Query(), User.class);
        System.out.println(users);

        //等值查询
        List<User> users1 = template.find(Query.query(Criteria.where("name").is("编程不良人")),
                User.class);
        System.out.println(users1);

        // > gt  < lt  >= gte  <= lte

        log.info("{}\n{}\n{}\n{}",
        template.find(Query.query(Criteria.where("age").lt(25)),
                User.class),
        template.find(Query.query(Criteria.where("age").gt(25)),
                User.class),
        template.find(Query.query(Criteria.where("age").lte(25)),
                User.class),
        template.find(Query.query(Criteria.where("age").gte(25)),
                User.class)
        );

        //and
        template.find(Query.query(Criteria.where("name").is("编程不良人")
                .and("age").is(23)), User.class);

        //or
        Criteria criteria = new Criteria()
                .orOperator(Criteria.where("name").is("编程不良人_1"),
                        Criteria.where("name").is("编程不良人_2"));
        template.find(Query.query(criteria), User.class);

        //and or
        Criteria criteria1 = new Criteria()
                .and("age").is(23)
                .orOperator(
                        Criteria.where("name").is("编程不良人_1"),
                        Criteria.where("name").is("编程不良人_2"));
        template.find(Query.query(criteria1), User.class);

        //sort 排序
        Query query = new Query();
        query.with(Sort.by(Sort.Order.desc("age")));//desc 降序  asc 升序
        template.find(query, User.class);


        //skip limit 分页
        Query queryPage = new Query();
        queryPage.with(Sort.by(Sort.Order.desc("age")))//desc 降序  asc 升序
                .skip(0) //起始条数
                .limit(4); //每页显示记录数
        template.find(queryPage, User.class);


        //count 总条数
        template.count(new Query(), User.class);

        //distinct 去重
        //参数 1:查询条件 参数 2: 去重字段  参数 3: 操作集合  参数 4: 返回类型
        System.out.println("distinct:" + template.findDistinct(new Query(), "name",
                User.class, String.class));

        //使用 json 字符串方式查询
        query = new BasicQuery(
                "{$or:[{name:'编程不良人'},{name:'徐凤年'}]}",
                "{name:0}");

        template.find(query, User.class);
    }


    @Test
    public void testUpdate() {
        //1.更新条件
        Query query = Query.query(Criteria.where("username").is("编程不良人"));
        //2.更新内容
        Update update = new Update();
        update.setOnInsert("id", 10);       // 当要更新的文档不存在时,设置更新插入操作插入文档的_id
        update.set("salary", 4000.1);

        //只更新符合条件的第一条数据
        template.updateFirst(query, update, User.class);
        //多条更新
        template.updateMulti(query, update, User.class);
        //更新插入（要更新的条件文档没查到，把新数据插入）
        UpdateResult updateResult = template.upsert(query, update, User.class);

//        返回值均为 updateResult
        System.out.println("匹配条数:" + updateResult.getMatchedCount());
        System.out.println("修改条数:" + updateResult.getModifiedCount());
        System.out.println("插入id_:" + updateResult.getUpsertedId());
    }

    @Test
    public void testDelete(){
        //删除所有
        template.remove(new Query(),User.class);
        //条件删除
        template.remove(
                Query.query(Criteria.where("name").is("编程不良人")),
                User.class
        );
    }


    /**   employee  **/
    private static final String EMPLOYEE_COLLECTION = "employee";

    // 建立数据
    @Test
    public void testDepart(){
        Random random = new Random();
        System.out.println(template.insertAll(Arrays.asList(
                new Employee().setName("西门庆").setDepartNo(20).setEmpNo(3).setGender("男").setHireDate(new Date()).setSalary(random.nextInt(5000)).setJob("经理"),
                new Employee().setName("潘金莲").setDepartNo(20).setEmpNo(4).setGender("女").setHireDate(new Date()).setSalary(random.nextInt(5000)).setJob("销售"),
                new Employee().setName("扈二娘").setDepartNo(20).setEmpNo(5).setGender("女").setHireDate(new Date()).setSalary(random.nextInt(5000)).setJob("销售"),
                new Employee().setName("李瓶儿").setDepartNo(20).setEmpNo(6).setGender("女").setHireDate(new Date()).setSalary(random.nextInt(5000)).setJob("销售"),
                new Employee().setName("白骨精").setDepartNo(20).setEmpNo(7).setGender("女妖").setHireDate(new Date()).setSalary(random.nextInt(5000)).setJob("销售"),
                new Employee().setName("春梅").setDepartNo(20).setEmpNo(8).setGender("男").setHireDate(new Date()).setSalary(random.nextInt(5000)).setJob("销售"),
                new Employee().setName("镇关西").setDepartNo(20).setEmpNo(9).setGender("男").setHireDate(new Date()).setSalary(random.nextInt(5000)).setJob("销售"),
                new Employee().setName("韦小宝").setDepartNo(20).setEmpNo(10).setGender("男").setHireDate(new Date()).setSalary(random.nextInt(5000)).setJob("销售"),
                new Employee().setName("武大郎").setDepartNo(30).setEmpNo(11).setGender("男").setHireDate(new Date()).setSalary(random.nextInt(5000)).setJob("销售"),
                new Employee().setName("武松").setDepartNo(30).setEmpNo(12).setGender("男").setHireDate(new Date()).setSalary(random.nextInt(5000)).setJob("前端"),
                new Employee().setName("吴用").setDepartNo(30).setEmpNo(54).setGender("男").setHireDate(new Date()).setSalary(random.nextInt(5000)).setJob("前端"),
                new Employee().setName("卢俊义").setDepartNo(30).setEmpNo(67).setGender("男").setHireDate(new Date()).setSalary(random.nextInt(5000)).setJob("前端"),
                new Employee().setName("柴进").setDepartNo(30).setEmpNo(77).setGender("男").setHireDate(new Date()).setSalary(random.nextInt(5000)).setJob("前端"),
                new Employee().setName("鲁智深").setDepartNo(30).setEmpNo(87).setGender("男").setHireDate(new Date()).setSalary(random.nextInt(5000)).setJob("前端"),
                new Employee().setName("林冲").setDepartNo(30).setEmpNo(76).setGender("男").setHireDate(new Date()).setSalary(random.nextInt(5000)).setJob("前端"),
                new Employee().setName("杨志").setDepartNo(30).setEmpNo(53).setGender("男").setHireDate(new Date()).setSalary(random.nextInt(5000)).setJob("前端"),
                new Employee().setName("顾大娘").setDepartNo(30).setEmpNo(32).setGender("女").setHireDate(new Date()).setSalary(random.nextInt(5000)).setJob("前端"),
                new Employee().setName("花荣").setDepartNo(30).setEmpNo(33).setGender("男").setHireDate(new Date()).setSalary(random.nextInt(5000)).setJob("后端"),
                new Employee().setName("呼延灼").setDepartNo(30).setEmpNo(34).setGender("男").setHireDate(new Date()).setSalary(random.nextInt(5000)).setJob("前端"),
                new Employee().setName("九纹龙史进").setDepartNo(30).setEmpNo(55).setGender("男").setHireDate(new Date()).setSalary(random.nextInt(5000)).setJob("前端"),
                new Employee().setName("阮小二").setDepartNo(30).setEmpNo(66).setGender("男").setHireDate(new Date()).setSalary(random.nextInt(5000)).setJob("测试"),
                new Employee().setName("阮小五").setDepartNo(30).setEmpNo(44).setGender("男").setHireDate(new Date()).setSalary(random.nextInt(5000)).setJob("测试"),
                new Employee().setName("刘唐").setDepartNo(30).setEmpNo(45).setGender("男").setHireDate(new Date()).setSalary(random.nextInt(5000)).setJob("测试"),
                new Employee().setName("高俅").setDepartNo(40).setEmpNo(46).setGender("男").setHireDate(new Date()).setSalary(random.nextInt(5000)).setJob("经理"),
                new Employee().setName("时迁").setDepartNo(40).setEmpNo(47).setGender("男").setHireDate(new Date()).setSalary(random.nextInt(5000)).setJob("运营"),
                new Employee().setName("黄英").setDepartNo(40).setEmpNo(48).setGender("男").setHireDate(new Date()).setSalary(random.nextInt(5000)).setJob("运营"),
                new Employee().setName("乐和").setDepartNo(40).setEmpNo(49).setGender("男").setHireDate(new Date()).setSalary(random.nextInt(5000)).setJob("运营"),
                new Employee().setName("孙立").setDepartNo(40).setEmpNo(50).setGender("男").setHireDate(new Date()).setSalary(random.nextInt(5000)).setJob("运营"),
                new Employee().setName("双枪将董平").setDepartNo(40).setEmpNo(51).setGender("男").setHireDate(new Date()).setSalary(random.nextInt(5000)).setJob("运营"),
                new Employee().setName("宋万").setDepartNo(40).setEmpNo(98).setGender("男").setHireDate(new Date()).setSalary(random.nextInt(5000)).setJob("打杂"),
                new Employee().setName("宋江").setDepartNo(50).setEmpNo(97).setGender("男").setHireDate(new Date()).setSalary(random.nextInt(5000)).setJob("经理"),
                new Employee().setName("李逵").setDepartNo(50).setEmpNo(96).setGender("男").setHireDate(new Date()).setSalary(random.nextInt(5000)).setJob("保镖")
                )));
    }


    @Test
    public void testUpdate1(){
        Query query = new Query();
        // departNo = 20 and job != 经理
        query.addCriteria(Criteria.where("departNo").is(20).and("job").ne("经理")); // key是对象的属性名，不是数据库中字段名
//        System.out.println(template.find(query, Employee.class));
        Update update = new Update();
        update.set("mgr", 3);
        template.updateMulti(query, update, Employee.class);
        System.out.println(template.find(query, Employee.class));
    }


    @Test
    public void testSort(){
        Query query = new Query();
        // order by salary limit 0, 20
        query.with(Sort.by(Sort.Order.asc("salary"))).skip(0).limit(20);
        List<Employee> list = template.find(query, Employee.class);
        list.forEach(System.out::println);
    }

    // distinct:去重，输出=[20, 30, 40, 50]
    @Test
    public void testDistinct(){
        System.out.println(template.findDistinct(new Query(), "departNo",
                 Employee.class, Integer.class));
    }

    // 联合查询，其实并不联合
    // 研发部所有员工
    @Test
    public void testQuery1(){
        Query dept = new Query().addCriteria(Criteria.where("depname").is("研发部"));
        int dptNo = template.findOne(dept, Department.class).getDepartNo();
        Query emp = new Query().addCriteria(Criteria.where("departNo").is(dptNo))
                .with(Sort.by(Sort.Order.desc("empNo"))).limit(10);
        template.find(emp, Employee.class).forEach(System.out::println);
    }

    // unset：删字段
    @Test
    public void testUnset(){
        System.out.println(template.updateMulti(new Query(), new Update().unset("comm"), Employee.class));
    }


    /* https://www.cnblogs.com/zaoyu/p/springboot-mongodb.html */
    /**
     * 用Aggregates和Bson构建聚合操作对象，用预先生成的MongoCollection对象调用aggregate执行即可。
     */
    @Test
    public void testCountWithAggregates(){
        MongoCollection<Document> collection = template.getCollection(EMPLOYEE_COLLECTION);
        // Aggregates提供各种操作符，返回一个Bson对象。这里用match，然后用Filters来实现过滤条件的构建，也是返回一个Bson对象。
        Bson matchBson = Aggregates.match(Filters.gt("salary", 3000));
        // 直接用Aggregates的count方法，如果不传自定义的名称，默认用“count”接收。
        Bson countBson = Aggregates.count("高薪");
        // 构建一个List<Bson>, 并把每一个聚合操作Bson加进去，最后传入aggregate方法中执行。
        List<Bson> bsonList = new ArrayList<>();
        bsonList.add(matchBson);
        bsonList.add(countBson);
        AggregateIterable<Document> resultList = collection.aggregate(bsonList);
        for (Document document : resultList) {
            System.out.println("result is :" + document);
        }
    }

    /**
     * 用Aggregation集合接收聚合操作，用MongoTemplate对象直接调用aggregate，传入聚合操作集合、表名、映射对象。
     */
    @Test
    public void testCountWithAggregation(){
        // 构建查询match条件：分数大于80
        MatchOperation matchOperation = Aggregation.match(Criteria.where("salary").gt(3000));
        // 构建count操作，用“高薪”名称接收
        CountOperation countOperation = Aggregation.count().as("高薪");
        // 传入多个aggregation（聚合操作），用Aggregation对象接收。
        Aggregation aggregation = Aggregation.newAggregation(matchOperation, countOperation);
        // 直接用mongoTemplate调用aggregate方法，传入aggregation集合，表名，还有用什么对象接收数据，这里我用Document接收，不再建类。
        AggregationResults<Document> resultList = template.aggregate(aggregation, EMPLOYEE_COLLECTION, Document.class);
        for (Document document : resultList) {
            System.out.println("result is :" + document);
        }
    }// 以上2个方法的输出结果一样，如下。result is :Document{{高薪=10}}

    /**
     * Aggregation 实现match, group, sort。
     * 日期比较
     */
    @Test
    public void testGroupAggregations() {
        // 第一阶段，过滤查询日期介于2022.10.01 - 2023.03.01 之间的数据，用Aggregation实现类MatchOperation接收。
        Calendar from = Calendar.getInstance();
        from.set(2022, Calendar.OCTOBER, 1);
        Calendar to = Calendar.getInstance();
        to.set(2023, Calendar.MARCH, 1);
        MatchOperation match = Aggregation.match(
                Criteria.where("hireDate").gte(new Date(from.getTimeInMillis()))
                        .andOperator(Criteria.where("hireDate").lte(new Date(to.getTimeInMillis()))));
        // 第二阶段，用ProjectionOperation接收，也是Aggregation的实现类。
        ProjectionOperation project = Aggregation.project("gender", "depno", "salary", "hireDate");
        // 第三阶段，分组统计，先按照日期分组，再统计每月入职员工的平均工资。
        GroupOperation group = Aggregation.group("depno", "gender")  // 这里用的是mongodb中的字段名，所以还真是需要统一命名啊！
                .avg("salary").as("平均工资")
                // 这里是计算文档条数
                .count().as("count");
        // 第四阶段，排序。升序展示。
        SortOperation sort = Aggregation.sort(Sort.Direction.ASC, "平均工资");
        // 用newAggregation接收以上多个阶段的管道聚合指令，执行，得到结果。
        Aggregation aggregations = Aggregation.newAggregation(match, project, group, sort);
        AggregationResults<Document> resultList = template.aggregate(aggregations, EMPLOYEE_COLLECTION, Document.class);
        for (Document document : resultList) {
            System.out.println("result is :" + document);
        }
        // 返回结果：_id为group key, 一个参数时为值，多个参数时为document
//    result is :Document{{_id=Document{{depno=20, gender=男}}, 平均工资=1121.0, count=2}}
//    result is :Document{{_id=Document{{depno=20, gender=女}}, 平均工资=1186.5, count=2}}
//    result is :Document{{_id=Document{{depno=30, gender=男}}, 平均工资=2681.3333333333335, count=9}}
//    result is :Document{{_id=Document{{depno=40, gender=男}}, 平均工资=2969.3333333333335, count=3}}
//    result is :Document{{_id=Document{{depno=50, gender=男}}, 平均工资=4684.0, count=1}}
    }


    @Test
    public void testCompareDate(){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -4);
        // 修改时间
//        Query query = new Query().addCriteria(Criteria.where("empNo").mod(7, 2));
//        Update update = new Update().set("hireDate", new Date(calendar.getTimeInMillis()));
//        template.updateMulti(query, update, Employee.class);
        // 比较日期 Date
        // java对象中的Date可以比较大小，mongoDB shell中生成的Date能转换成Date，但不能比较大小
        template.find(new Query()
                        .addCriteria(Criteria.where("hireDate").gt(new Date(calendar.getTimeInMillis())))
                        .with(Sort.by(Sort.Order.desc("hireDate"))), Employee.class)
                .forEach(System.out::println);

//        template.find(new Query(), Employee.class).forEach(System.out::println);
    }
}
