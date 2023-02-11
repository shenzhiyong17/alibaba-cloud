package szy.alibaba.cloud.controller;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.Tracer;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRuleManager;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
public class SentinelController {

    private static final String HELLO_RESOURCE_NAME = "hello";
    private static final String USER_RESOURCE_NAME = "user";

    private static final String DEGRADE_RESOURCE_NAME = "degrade";

    @PostConstruct
    private static void init() {
        //流控规则
        List<FlowRule> rules = new ArrayList<>();
        //hello资源流控
        FlowRule rule = new FlowRule();
        //设置资源
        rule.setResource(HELLO_RESOURCE_NAME);
        //设置流控规则， QPS
        rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        // 资源阈值=1
        rule.setCount(1);
        rules.add(rule);

        // user资源流控
        FlowRule userRule = new FlowRule(USER_RESOURCE_NAME);
        userRule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        userRule.setCount(1);
        rules.add(userRule);

        FlowRuleManager.loadRules(rules);

        // 熔断降级规则
        List<DegradeRule> degradeRules = new ArrayList<>();
        DegradeRule degradeRule = new DegradeRule(DEGRADE_RESOURCE_NAME);
        // 设置熔断规则：异常数触发
        degradeRule.setGrade(RuleConstant.DEGRADE_GRADE_EXCEPTION_COUNT);
        // 时间段，默认1s
        degradeRule.setStatIntervalMs(60 * 1000);
        // 触发熔断最小请求数
        degradeRule.setMinRequestAmount(2);
        // 触发熔断异常数
        degradeRule.setCount(2);
        // 熔断持续时长，单位秒
        // 设置10s，10s之后进入半开状态：如果第一次请求失败，再次熔断
        degradeRule.setTimeWindow(10);

        degradeRules.add(degradeRule);
        DegradeRuleManager.loadRules(degradeRules);
    }

    @RequestMapping(value = "hello", method = RequestMethod.GET)
    public String hello() throws InterruptedException {
//        Thread.sleep(500);
//        log.info("hello id {}", id);
        Entry entry = null;
        try {
            // sentinel针对资源进行限制
            entry = SphU.entry(HELLO_RESOURCE_NAME);
            String str = "hello world";
            log.info("==={}===", str);
            return str;
        } catch (BlockException c) {
            // 资源访问阻止，被限流或降级
            log.info("block!");
            return "流控";
        } catch (Exception e) {
            // 降级规则，记录业务异常
            Tracer.traceEntry(e, entry);
        } finally {
            if (entry != null) {
                entry.exit();
            }
        }
        return null;
    }

    /**
     * @return
     * @SentinelResource 使用：
     * 1、配置bean：SentinelResourceAspect
     * 2、配置保护资源 value = USER_RESOURCE_NAME
     * 3、配置降级处理：blockHandler = "blockUser" （默认在同一个类中）
     * 4、配置异常处理：fallback = "fallbackUser"
     */
    @GetMapping("user")
    @SentinelResource(value = USER_RESOURCE_NAME,
            blockHandler = "blockUser",
            fallback = "fallbackUser")
    public String user(String name) {
        return name;
    }


    /**
     * 一定要是public
     * 参数、返回值要保持一致
     *
     * @BlockException 用于区分处理规则
     */
    public String blockUser(String name, BlockException b) {
        return "block user";
    }


    public String fallbackUser(String name, Throwable throwable) {
        return "fallback user";
    }
}
