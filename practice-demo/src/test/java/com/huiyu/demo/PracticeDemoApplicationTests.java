package com.huiyu.demo;

import com.huiyu.demo.domain.CustomerInfo;
import com.huiyu.demo.domain.User;
import com.huiyu.demo.service.CustomerService;
import com.huiyu.demo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
class PracticeDemoApplicationTests {

    @Resource
    private CustomerService customerService;
    @Resource
    private UserService userService;

    @Test
    void contextLoads() {
        List<CustomerInfo> list = customerService.getCustomerCodeFromApi(Arrays.asList("1", "2"));
        log.info("list:{}", list);
    }
    @Test
    void test1(){
        List<User> users = new ArrayList<>();
        CustomerInfo info = new CustomerInfo();
        User user = new User();
        user.setAge(28);
        user.setDeleted(false);
        user.setEnabled(false);
        user.setCustomerCode("1");
        users.add(user);
        User user1  = new User();
        user1.setAge(32);
        user1.setDeleted(false);
        user1.setEnabled(false);
        user1.setCustomerCode("2");
        users.add(user1);
        Map<String, List<User>> stringListMap = userService.groupUser(users);
        List<User> users1 = stringListMap.get("young");
        List<User> users2 = stringListMap.get("midAge");
        System.out.println(users1);
        System.out.println(users2);

    }

}
