package com.mysite.service.impl;

import com.github.pagehelper.PageInfo;
import com.mysite.model.entity.Log;
import com.mysite.service.LogService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LogServiceImplTest {

    @Autowired
    private LogService logService;

    @Test
    public void addLog() {
    }

    @Test
    public void deleteLogById() {
    }

    @Test
    public void getLogs() {
        PageInfo<Log> logs = logService.getLogs(1, 10);
        System.out.println(logs);
    }
}