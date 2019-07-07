package com.mysite.dao;

import com.mysite.model.dto.ArchiveDto;
import com.mysite.model.dto.cond.ContentCond;
import com.mysite.model.entity.Content;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 文章持久层
 * Created by Donghua.Chen on 2018/4/29.
 */
@Mapper
public interface ContentDao {

    /**
     * 添加文章
     *
     * @param content
     * @return
     */
    int addArticle(Content content);

    /**
     * 根据编号删除文章
     *
     * @param cid
     * @return
     */
    int deleteArticleById(@Param("cid") Integer cid);

    /**
     * 更新文章
     *
     * @param content
     * @return
     */
    int updateArticleById(Content content);

    /**
     * 更新文章的评论数
     *
     * @param cid
     * @param commentsNum
     * @return
     */
    int updateArticleCommentCountById(@Param("cid") Integer cid, @Param("commentsNum") Integer commentsNum);


    /**
     * 根据编号获取文章
     *
     * @param cid
     * @return
     */
    Content getArticleById(@Param("cid") Integer cid);

    /**
     * 根据条件获取文章列表
     *
     * @param contentCond
     * @return
     */
    List<Content> getArticlesByCond(ContentCond contentCond);

    /**
     * 获取文章总数量
     *
     * @return
     */
    Long getArticleCount();

    /**
     * 获取归档数据
     *
     * @param contentCond 查询条件（只包含开始时间和结束时间）
     * @return
     */
    List<ArchiveDto> getArchive(ContentCond contentCond);

    /**
     * 获取最近的文章（只包含id和title）
     *
     * @return
     */
    List<Content> getRecentlyArticle();

    /**
     * 搜索文章-根据标题 或 内容匹配
     *
     * @param param
     * @return
     */
    List<Content> searchArticle(@Param("param") String param);

}
