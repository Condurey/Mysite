package com.mysite.service;

import com.github.pagehelper.PageInfo;
import com.mysite.model.dto.cond.ContentCond;
import com.mysite.model.entity.Content;

/**
 * 文章服务层
 * Created by Donghua.Chen on 2018/4/29.
 */
public interface ContentService {

    /**
     * 添加文章
     *
     * @param content
     * @return
     */
    void addArticle(Content content);

    /**
     * 根据编号删除文章
     *
     * @param cid
     * @return
     */
    void deleteArticleById(Integer cid);

    /**
     * 更新文章
     *
     * @param content
     * @return
     */
    void updateArticleById(Content content);

    /**
     * 更新分类
     *
     * @param ordinal
     * @param newCatefory
     */
    void updateCategory(String ordinal, String newCatefory);


    /**
     * 添加文章点击量
     *
     * @param content
     */
    void updateContentByCid(Content content);

    /**
     * 根据编号获取文章
     *
     * @param cid
     * @return
     */
    Content getAtricleById(Integer cid);

    /**
     * 根据条件获取文章列表
     *
     * @param contentCond
     * @return
     */
    PageInfo<Content> getArticlesByCond(ContentCond contentCond, int pageNum, int pageSize);

    /**
     * 获取最近的文章（只包含id和title）
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageInfo<Content> getRecentlyArticle(int pageNum, int pageSize);

    /**
     * 搜索文章
     *
     * @param param
     * @param pageNun
     * @param pageSize
     * @return
     */
    PageInfo<Content> searchArticle(String param, int pageNun, int pageSize);
}
