package com.mysite.dao;

import com.mysite.model.po.Comment;
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
public class CommentDaoTest {

    @Resource
    private CommentDao commentDao;

    @Test
    @Transactional
    public void deleteComment() {
        int count = commentDao.deleteComment(1);
        System.out.println(count);
    }


    @Test
    public void testGetCommentById() {
        Comment comment = commentDao.getCommentById(1);
        System.out.println(comment);
    }


}