package com.mysite.dao;

import com.mysite.model.po.Comment;
import com.mysite.model.query.CommentQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 评论实体类
 * Created by Donghua.Chen on 2018/4/29.
 */
@Mapper
public interface CommentDao {

    /**
     * 新增评论
     *
     * @param comment
     * @return
     */
    int addComment(Comment comment);

    /**
     * 删除评论
     *
     * @param coid
     * @return
     */
    int deleteComment(@Param("coid") Integer coid);

    /**
     * 更新评论的状态
     *
     * @param coid
     * @return
     */
    int updateCommentStatus(@Param("coid") Integer coid, @Param("status") String status);

    /**
     * 获取单条评论
     *
     * @param coid
     * @return
     */
    Comment getCommentById(@Param("coid") Integer coid);

    /**
     * 根据文章编号获取评论列表
     *
     * @param cid
     * @return
     */
    List<Comment> getCommentsByCId(@Param("cid") Integer cid);

    /**
     * 根据条件获取评论列表
     *
     * @param commentQuery
     * @return
     */
    List<Comment> getCommentsByCond(CommentQuery commentQuery);

    /**
     * 获取文章数量
     *
     * @return
     */
    Long getCommentsCount();
}
