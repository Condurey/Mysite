package com.mysite.dao;

import com.mysite.model.entity.Content;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.AutoConfigureMybatis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMybatis
public class ContentDaoTest {

    @Autowired
    private ContentDao contentDao;

    @Test
    public void testGetArticleById() {
        Content content = contentDao.getArticleById(1);
        System.out.println(content);
    }


}