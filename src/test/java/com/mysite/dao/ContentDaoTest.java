package com.mysite.dao;

import com.mysite.constant.Types;
import com.mysite.model.po.Content;
import com.mysite.model.query.ContentQuery;
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
public class ContentDaoTest {

    @Resource
    private ContentDao contentDao;

    @Test
    @Transactional
    public void deleteArticleById() {
        int count = contentDao.deleteArticleById(1);
        System.out.println(count);
    }

    @Test
    public void getArticleById() {
        Content content = contentDao.getArticleById(1);
        System.out.println(content);
    }

    @Test
    public void getArticlesByCond() {
        ContentQuery contentQuery = new ContentQuery();
        contentQuery.setType(Types.ARTICLE.getType());
        List<Content> contents = contentDao.getArticlesByCond(contentQuery);
        System.out.println(contents);
    }



}