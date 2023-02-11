package szy.alibaba.cloud.user.controller;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import szy.alibaba.cloud.stock.api.StockApi;
import szy.alibaba.cloud.stock.module.Stock;
import szy.alibaba.cloud.user.api.UserApi;
import szy.alibaba.cloud.user.mapper.UserMapper;
import szy.alibaba.cloud.user.moudle.User;
import szy.alibaba.cloud.user.service.LoginService;

@RestController
@Slf4j
public class UserController implements UserApi {

    @Autowired
    UserMapper userMapper;

    @Autowired
    LoginService loginService;

    @Autowired
    StockApi stockApi;


    @PostMapping(value = "/login")
    public String login(@RequestBody User user){
        return loginService.login(user);
    }

    @Override
    public User getById(long id) {
        return userMapper.selectById(id);
    }

    @Override
    public int insert(@RequestBody User user) {
        return userMapper.insert(user);
    }

    @Override
    public User queryUser(@RequestBody User user) {
        Wrapper<User> wrapper = new QueryWrapper<User>()
                .eq(user.getUsername() != null, "username", user.getUsername())
                .eq(user.getNickname() != null, "nickname", user.getNickname())
                .eq(user.getPhone() != null, "phone", user.getPassword())
                .eq(user.getAge() > 0, "age", user.getAge())
                .eq(user.getGender() != null, "gender", user.getGender());
        return userMapper.selectOne(wrapper);
    }

    @Override
    public int update(@RequestBody User user) {
        return 0;
    }

    @GetMapping("/getStock")
    public Stock getStock(int id){
        log.info("get stock {}", id);
        return stockApi.getStock(id);
    }
}
