package szy.alibaba.cloud.user.api;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import szy.alibaba.cloud.user.moudle.User;

@FeignClient("user-service")
public interface UserApi {


    @RequestMapping(value = "getById", method = RequestMethod.GET)
    User getById(@RequestParam("id") long id);

    @RequestMapping(value = "insert", method = RequestMethod.POST)
    int insert(@RequestBody User user);

    @RequestMapping(value = "queryUser", method = RequestMethod.POST)
    User queryUser(@RequestBody User user);

    @RequestMapping(value = "update", method = RequestMethod.POST)
    int update(@RequestBody User user);
}
