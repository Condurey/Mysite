package com.mysite.service.impl;

import com.github.pagehelper.PageInfo;
import com.mysite.model.po.Log;
import com.mysite.service.LogService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LogServiceImplTest {

    @Autowired
    private LogService logService;

    @Test
    @Transactional
    public void addLog() {
        Log log = new Log();
        log.setId(30);
        log.setAction("testLog");
        log.setIp("127.0.0.1");
        logService.addLog("testLog", "nonono", "127.0.0.1", 2);
    }

    @Test
    @Transactional
    public void deleteLogById() {
        logService.deleteLogById(29);
    }

    @Test
    public void getLogs() {
        PageInfo<Log> logs = logService.getLogs(1, 10);
        System.out.println(logs);
    }
}