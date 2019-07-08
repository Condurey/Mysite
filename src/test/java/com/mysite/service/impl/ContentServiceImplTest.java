package com.mysite.service.impl;

import com.mysite.model.entity.Content;
import com.mysite.service.ContentService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ContentServiceImplTest {
    @Autowired
    private ContentService contentService;

    @Test
    public void addArticle() {
    }

    @Test
    public void deleteArticleById() {
    }

    @Test
    public void updateArticleById() {
    }

    @Test
    public void updateCategory() {
    }

    @Test
    public void updateContentByCid() {
    }

    @Test
    public void getAtricleById() {
        Content content = contentService.getAtricleById(1);
        System.out.println(content);

    }

    @Test
    public void getArticlesByCond() {
    }

    @Test
    public void getRecentlyArticle() {
    }

    @Test
    public void searchArticle() {
    }
}