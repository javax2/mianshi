package com.huiyu.demo.service;

import com.huiyu.demo.domain.CustomerInfo;
import com.huiyu.demo.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 需求: 完善groupUser和groupUserAndFillPhone方法
 */
@Service
public class UserService {
    @Autowired
    private CustomerService customerService;

    /**
     * 按照年龄分组, 30岁以下为"青年组", 30岁以上为"中年组", 过滤掉已删除(deleted=true)或不可用(enabled=false)用户
     */
    public Map<String, List<User>> groupUser(List<User> userList) {
        Map<String,List<User>> group = new HashMap<>();
        List<User> yonug = new ArrayList<>();
        List<User> midAge = new ArrayList<>();
        //TODO:
        for (User user : userList) {
            if (!user.getDeleted()&&!user.getEnabled()){
                if (user.getAge() < 30){
                    yonug.add(user);
                    group.put("young",yonug);
                }else {
                    midAge.add(user);
                    group.put("midAge",midAge);
                }
            }
        }
        return group;
    }

    /**
     * 按照年龄分组, 30岁以下为"青年组", 30岁以上为"中年组", 过滤掉已删除或不可用用户，并填充号码
     * 号码从CustomerService.getCustomerCodeFromApi 获取
     */
    public Map<String, List<User>> groupUserAndFillPhone(List<User> userList) {
        List<User> users =  new ArrayList<>();
        List<String> customerCodeList = new ArrayList<>();
        for (User user : userList) {
            if (!user.getDeleted()&&!user.getEnabled()){
                customerCodeList.add(user.getCustomerCode());
                users.add(user);
            }
        }
        List<CustomerInfo> customerCodeFromApi = customerService.getCustomerCodeFromApi(customerCodeList);
        for (CustomerInfo info : customerCodeFromApi) {
            for (User user : users) {
                if (info.getCustomerCode() == user.getCustomerCode()){
                    user.setPhone(info.getPhone());
                }
            }
        }
        return this.groupUser(users);
    }
}
