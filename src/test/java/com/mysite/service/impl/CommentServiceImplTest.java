package com.mysite.service.impl;

import com.mysite.service.CommentService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CommentServiceImplTest {

    @Autowired
    private CommentService commentService;

    @Test
    public void addComment() {
    }

    @Test
    public void deleteComment() {
    }

    @Test
    public void updateCommentStatus() {
    }

    @Test
    public void getCommentById() {
    }

    @Test
    public void getCommentsByCId() {
    }

    @Test
    public void getCommentsByCond() {
    }
}