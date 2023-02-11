package szy.alibaba.cloud.user.filter;

import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.junit.platform.commons.util.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import szy.alibaba.cloud.user.module.LoginUser;
import szy.cloud.common.utils.JwtUtils;
import szy.cloud.common.utils.MapCache;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Component
// 解析token，从缓存读取用户信息存入context
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {


    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {
        // 获取token
        String token = httpServletRequest.getHeader("token");
        if (StringUtils.isBlank(token)){
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }

        // 解析token
        String userId;
        try {
            Claims claims = JwtUtils.parseJWT(token);
            userId = claims.getId();
        }catch (Exception e){
            log.error("parseJWT error {}", ExceptionUtils.getStackTrace(e));
            throw new RuntimeException("token 非法");
        }

        // 从redis中获取用户信息
        // 本地沒有redis，用全局Map代替
        String redisKey = "login:" + userId;
        LoginUser loginUser = MapCache.get(redisKey);
        if (Objects.isNull(loginUser)){
            throw new RuntimeException("用户未登录");
        }

        // 存入SecurityContextHolder
        // todo: 获取权限信息填入第三个参数
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUser, null, null);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        // 放行
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
