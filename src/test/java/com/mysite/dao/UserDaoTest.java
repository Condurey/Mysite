package com.mysite.dao;

import com.mysite.model.po.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.AutoConfigureMybatis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMybatis
//@MybatisTest
//@SpringBootApplication
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserDaoTest {

    @Autowired
    private UserDao userDao;

    @Test
    public void testGetUserInfo() {
        User user = userDao.getUserInfoById(1);
        System.out.println(user);
    }


}