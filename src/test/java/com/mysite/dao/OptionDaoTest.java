package com.mysite.dao;

import com.mysite.model.entity.Option;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.AutoConfigureMybatis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMybatis
public class OptionDaoTest {

    @Autowired
    private OptionDao optionDao;

    @Test
    public void testGetOptions() {
        List<Option> options = optionDao.getOptions();
        System.out.println(options);
    }

}