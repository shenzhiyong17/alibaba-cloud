package szy.alibaba.cloud.user.exception;

import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.BlockExceptionHandler;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityException;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeException;
import com.alibaba.csp.sentinel.slots.block.flow.FlowException;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowException;
import com.alibaba.csp.sentinel.slots.system.SystemBlockException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import szy.alibaba.cloud.user.module.Result;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 默认的sentinel异常处理类
 * 如果方法不加@SentinelResource注解，命中在dashboard中配置的规则后在此处理
 */
@Component
@Slf4j
public class MyBlockExceptionHandler implements BlockExceptionHandler {
    @Override
    public void handle(HttpServletRequest httpServletRequest,
                       HttpServletResponse httpServletResponse, BlockException e) throws Exception {
        log.info("BlockException = {}", e.getRule());

        Result r = null;
        if (e instanceof DegradeException){
            r = Result.error(101, "降级");
        }
        if (e instanceof FlowException){
            r = Result.error(102, "流控");
        }
        if (e instanceof ParamFlowException){
            r = Result.error(103, "热点参数限流");
        }
        if (e instanceof SystemBlockException){
            r = Result.error(104, "触发系统保护规则");
        }
        if (e instanceof AuthorityException){
            r = Result.error(105, "授权规则不通过");
        }


        httpServletResponse.setStatus(500);
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(httpServletResponse.getWriter(), r);

    }
}
