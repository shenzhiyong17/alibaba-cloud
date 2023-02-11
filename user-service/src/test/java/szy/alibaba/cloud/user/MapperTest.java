package szy.alibaba.cloud.user;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import szy.alibaba.cloud.user.mapper.UserMapper;
import szy.alibaba.cloud.user.moudle.User;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class MapperTest {

    @Autowired
    UserMapper userMapper;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    public void testUserMapper(){
        assert userMapper != null;
//
//        User user = new User();
//        user.setUsername("szy1");
//        user.setPassword("1234");
//        user.setPhone("1234567889");
//        userMapper.insert(user);

        System.out.println(userMapper.selectList(new QueryWrapper<User>().eq("username", "szy1")));
        System.out.println(userMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getUsername, "szy")));
    }

    @Test
    public void testPasswordEncoder(){
        assert passwordEncoder != null;

        String encode = passwordEncoder.encode("1234");
        String encode1 = passwordEncoder.encode("1234");
        System.out.println(encode);
        System.out.println(encode1);
        System.out.println(passwordEncoder.matches("1234", "$2a$10$cJTVbjEJNHNfrAmBK8qNkeZMHDfaO219TWhnJYjJ9UZfdtFUOqhxq"));
    }

}
