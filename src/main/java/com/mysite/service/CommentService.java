package com.mysite.service;

import com.github.pagehelper.PageInfo;
import com.mysite.model.po.Comment;
import com.mysite.model.query.CommentQuery;

import java.util.List;

/**
 * 评论服务层
 * Created by Donghua.Chen on 2018/4/29.
 */
public interface CommentService {

    /**
     * 新增评论
     *
     * @param comment 评论的实体
     * @return
     */
    void addComment(Comment comment);

    /**
     * 删除评论
     *
     * @param coid 评论的主键编号
     * @return
     */
    void deleteComment(Integer coid);

    /**
     * 更新评论的状态
     *
     * @param coid   评论的主键编号
     * @param status 状态
     * @return
     */
    void updateCommentStatus(Integer coid, String status);


    /**
     * 查找单条评论
     *
     * @param coid
     * @return
     */
    Comment getCommentById(Integer coid);

    /**
     * 根据文章编号获取评论列表--只显示通过审核的评论-正常状态的
     *
     * @param cid 文章主键编号
     * @return
     */
    List<Comment> getCommentsByCId(Integer cid);

    /**
     * 根据条件获取评论列表
     *
     * @param commentQuery 查询条件
     * @param pageNum     分页参数 第几页
     * @param pageSize    分页参数 每页条数
     * @return
     */
    PageInfo<Comment> getCommentsByCond(CommentQuery commentQuery, int pageNum, int pageSize);
}
