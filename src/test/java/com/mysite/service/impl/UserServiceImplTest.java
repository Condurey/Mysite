package com.mysite.service.impl;

import com.mysite.model.entity.User;
import com.mysite.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceImplTest {
    @Autowired
    private UserService userService;

    @Test
    @Transactional
    public void updateUserInfo() {
        User user = new User();
        user.setUid(1);
        user.setScreenName("hahaha");
        userService.updateUserInfo(user);
    }

    @Test
    public void getUserInfoById() {
        User user = userService.getUserInfoById(1);
        System.out.println(user);
    }

    @Test
    public void login() {
        User user = userService.login("admin", "123456");
        System.out.println(user);
    }
}