package com.mysite.service.impl;

import com.mysite.model.dto.StatisticsDto;
import com.mysite.model.po.Comment;
import com.mysite.model.po.Content;
import com.mysite.service.SiteService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SiteServiceImplTest {

    @Autowired
    private SiteService siteService;

    @Test
    public void getComments() {
        List<Comment> commentList = siteService.getComments(2);
        System.out.println(commentList);
    }

    @Test
    public void getNewArticles() {
        List<Content> contentList = siteService.getNewArticles(2);
        System.out.println(contentList);
    }

    @Test
    public void getComment() {
    }

    @Test
    public void getStatistics() {
        StatisticsDto statisticsDto = siteService.getStatistics();
        System.out.println(statisticsDto);
    }

    @Test
    public void getArchivesSimple() {
    }

    @Test
    public void getArchives() {
    }

    @Test
    public void getMetas() {
    }
}