package szy.alibaba.cloud.user.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import szy.alibaba.cloud.user.module.LoginUser;
import szy.alibaba.cloud.user.moudle.User;
import szy.cloud.common.utils.JwtUtils;
import szy.cloud.common.utils.MapCache;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
@Slf4j
public class LoginService {

    @Autowired
    AuthenticationManager authenticationManager;

    public String login(User user){
        // AuthenticationManager authenticate 进行认证
        // 从此处调到UserDetailService的 loadUserByUsername()
        log.info("user: {}", user);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);

        if (Objects.isNull(authenticate)){
            throw new RuntimeException("登陆失败");
        }

        // 认证通过后，userId生成jwt，存token返回
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        String userId = loginUser.getUser().getId().toString();
        String jwt = JwtUtils.createJWT(userId);
        Map<String, String> result = new HashMap<>();
        result.put("token", jwt);


        // 完整用户信息存缓存
        MapCache.put("login:" + userId, loginUser);
        return result.toString();
    }

}
