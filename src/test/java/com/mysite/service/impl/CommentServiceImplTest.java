package com.mysite.service.impl;

import com.mysite.model.po.Comment;
import com.mysite.service.CommentService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

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
        Comment comment = commentService.getCommentById(1);
        System.out.println(comment);
    }

    @Test
    public void getCommentsByCId() {
        List<Comment> commentList = commentService.getCommentsByCId(6);
        System.out.println(commentList);
    }

    @Test
    public void getCommentsByCond() {
    }
}