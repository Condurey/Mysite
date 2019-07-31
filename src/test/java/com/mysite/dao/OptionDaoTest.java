package com.mysite.dao;

import com.mysite.model.po.Option;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.AutoConfigureMybatis;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMybatis
public class OptionDaoTest {

    @Resource
    private OptionDao optionDao;

    @Test
    public void testGetOptions() {
        List<Option> options = optionDao.getOptions();
        System.out.println(options);
    }

}