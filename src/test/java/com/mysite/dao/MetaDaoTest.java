package com.mysite.dao;

import com.mysite.model.po.Meta;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.AutoConfigureMybatis;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMybatis
public class MetaDaoTest {

    @Resource
    private MetaDao metaDao;

    @Test
    public void testGetMetaById() {
        Meta meta = metaDao.getMetaById(1);
        System.out.println(meta);
    }

}