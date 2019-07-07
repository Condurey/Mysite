package com.mysite.dao;

import com.mysite.model.entity.Meta;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.AutoConfigureMybatis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMybatis
public class MetaDaoTest {

    @Autowired
    private MetaDao metaDao;

    @Test
    public void testGetMetaById() {
        Meta meta = metaDao.getMetaById(1);
        System.out.println(meta);
    }

}