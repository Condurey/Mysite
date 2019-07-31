package com.mysite.dao;

import com.mysite.model.dto.AttachDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.AutoConfigureMybatis;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMybatis
public class AttachDaoTest {

    @Resource
    private AttachDao attachDao;

    @Test
    @Transactional
    public void deleteAttach() {
        int count = attachDao.deleteAttach(1);
        System.out.println(count);
    }


    @Test
    public void testGetAttachById() {
        AttachDto attachDto = attachDao.getAttachById(1);
        System.out.println(attachDto);
    }


}