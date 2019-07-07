package com.mysite.dao;

import com.mysite.model.entity.Log;
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
public class LogDaoTest {

    @Autowired
    private LogDao logDao;

    @Test
    public void testGetLogs() {
        List<Log> logs = logDao.getLogs();
        System.out.println(logs);
    }

}