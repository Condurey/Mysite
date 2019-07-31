package com.mysite.dao;

import com.mysite.model.po.Log;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.AutoConfigureMybatis;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMybatis
public class LogDaoTest {

    @Resource
    private LogDao logDao;

    @Test
    @Transactional
    public void deleteLogById() {
        int count = logDao.deleteLogById(29);
        System.out.println(count);
    }

    @Test
    public void testGetLogs() {
        List<Log> logs = logDao.getLogs();
        System.out.println(logs);
    }

}