package szy.alibaba.cloud.user.service;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import szy.alibaba.cloud.user.mapper.UserMapper;
import szy.alibaba.cloud.user.module.LoginUser;
import szy.alibaba.cloud.user.moudle.User;

import java.util.Objects;

@Slf4j
@Service
// UserDetailsService 默认实现类为从内存查找用户，此类改写为从数据库查用户信息
// UsernamePasswordAuthenticationFilter 最终通过此类读取用户信息和权限信息
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 查询用户信息
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername, username);
        User user = userMapper.selectOne(queryWrapper);
        if (Objects.isNull(user)){
            throw new RuntimeException("用户名错误");
        }

        // todo: 查询权限

        //封装userDetails
        return new LoginUser(user);
    }

}
