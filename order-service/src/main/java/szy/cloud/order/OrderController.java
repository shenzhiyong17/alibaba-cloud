package szy.cloud.order;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.*;
import szy.cloud.order.api.OrderApi;
import szy.cloud.order.mapper.OrderMapper;
import szy.cloud.order.module.Order;

import javax.annotation.PostConstruct;
import java.util.Date;

@RestController
@Slf4j
@RefreshScope  // 通过@Value获取属性，如果属性在外部定义，比如nacos，需要加此注解获得刷新
public class OrderController implements OrderApi, BeanNameAware { // 实现xxAware只是为了获取BeanName、BeanFactory、BeanClassLoader

    private Order beer;
    private Order whisky;
    private String name;

    @Autowired
    OrderMapper orderMapper;

    @Autowired
    ApplicationContext applicationContext;

    @Value("${company.locate}")
    private String locate;

    @PostConstruct  // 初始化，相当于实现InitializingBean
    private void init(){
        this.beer = new Order();
        this.beer.setUserName("张三");
        this.beer.setPrice(9);
        this.beer.setId(2);
        this.beer.setPayTime(System.currentTimeMillis());

        this.whisky = new Order();
        this.whisky.setPayTime(System.currentTimeMillis());
        this.whisky.setUserName("李四");
        this.whisky.setId(3);
        this.whisky.setPrice(200);
    }

    @Override
    @RequestMapping(value = "/getOrder", method = RequestMethod.GET)
    public Order getOrder(int id) {
        log.info("getOrder {}", id);
        return orderMapper.getById(id);
    }

    @Override
    @RequestMapping(value = "insert", method = RequestMethod.POST)
    public Integer insert(@RequestBody Order order) {
        log.info("insert order {}", order);
        return orderMapper.insert(order);
    }

    @Override
    public Integer update(@RequestBody Order order) {
        log.info("update order {}", order);
        return orderMapper.update(order);
    }

    @Override
    public Integer delete(int id) {
        log.info("delete order id {}", id);
        return orderMapper.delete(id);
    }

    @Override
    public void setBeanName(String s) {  // 实例化-->AutoWired-->xxAward(用来获取BeanName、BeanFactory、BeanClassLoader）
        this.name = s;
        log.info("beanName is {}", s);
    }

    @RequestMapping(value = "/getConfig", method = RequestMethod.GET)
    public String getConfig(@RequestParam String key){
        log.info("key: {}", key);
        return applicationContext.getEnvironment().getProperty(key);  // 读nacos的配置
    }

    @RequestMapping(value = "/getLocate",method = RequestMethod.GET)
    public String getLocate(){
        return locate;
    }

    public static void main(String[] args){
        System.out.println(System.currentTimeMillis());
    }
}
