package com.mysite.service;

import com.mysite.model.dto.ArchiveDto;
import com.mysite.model.dto.MetaDto;
import com.mysite.model.dto.StatisticsDto;
import com.mysite.model.po.Comment;
import com.mysite.model.po.Content;
import com.mysite.model.query.ContentQuery;

import java.util.List;

/**
 * 站点服务
 * Created by Donghua.Chen on 2018/4/30.
 */
public interface SiteService {

    /**
     * 获取评论列表
     *
     * @param limit
     * @return
     */
    List<Comment> getComments(int limit);

    /**
     * 获取最新的文章
     *
     * @param limit
     * @return
     */
    List<Content> getNewArticles(int limit);

    /**
     * 获取单条评论
     *
     * @param coid
     * @return
     */
    Comment getComment(Integer coid);

    /**
     * 获取 后台统计数据
     *
     * @return
     */
    StatisticsDto getStatistics();

    /**
     * 获取归档列表 - 只是获取日期和数量
     *
     * @param contentQuery
     * @return
     */
    List<ArchiveDto> getArchivesSimple(ContentQuery contentQuery);

    /**
     * 获取归档列表
     *
     * @param contentQuery 查询条件（只包含开始时间和结束时间）
     * @return
     */
    List<ArchiveDto> getArchives(ContentQuery contentQuery);


    /**
     * 获取分类/标签列表
     *
     * @param type
     * @param orderBy
     * @param limit
     * @return
     */
    List<MetaDto> getMetas(String type, String orderBy, int limit);
}
