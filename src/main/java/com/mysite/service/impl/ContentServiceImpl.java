package com.mysite.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mysite.constant.ErrorConstant;
import com.mysite.constant.Types;
import com.mysite.constant.WebConst;
import com.mysite.dao.CommentDao;
import com.mysite.dao.ContentDao;
import com.mysite.exception.BusinessException;
import com.mysite.model.dto.cond.ContentCond;
import com.mysite.model.entity.Comment;
import com.mysite.model.entity.Content;
import com.mysite.model.entity.Relationship;
import com.mysite.service.ContentService;
import com.mysite.service.MetaService;
import com.mysite.service.RelationshipService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Donghua.Chen on 2018/4/29.
 */
@Service
public class ContentServiceImpl implements ContentService {

    @Autowired
    private ContentDao contentDao;

    @Autowired
    private CommentDao commentDao;

    @Autowired
    private MetaService metaService;

    @Autowired
    private RelationshipService relationshipService;


    @Transactional
    @Override
    @CacheEvict(value = {"atricleCache", "atricleCaches"}, allEntries = true, beforeInvocation = true)
    public void addArticle(Content contentDomain) {
        if (null == contentDomain)
            throw BusinessException.withErrorCode(ErrorConstant.Common.PARAM_IS_EMPTY);
        if (StringUtils.isBlank(contentDomain.getTitle()))
            throw BusinessException.withErrorCode(ErrorConstant.Article.TITLE_CAN_NOT_EMPTY);
        if (contentDomain.getTitle().length() > WebConst.MAX_TITLE_COUNT)
            throw BusinessException.withErrorCode(ErrorConstant.Article.TITLE_IS_TOO_LONG);
        if (StringUtils.isBlank(contentDomain.getContent()))
            throw BusinessException.withErrorCode(ErrorConstant.Article.CONTENT_CAN_NOT_EMPTY);
        if (contentDomain.getContent().length() > WebConst.MAX_TEXT_COUNT)
            throw BusinessException.withErrorCode(ErrorConstant.Article.CONTENT_IS_TOO_LONG);

        //标签和分类
        String tags = contentDomain.getTags();
        String categories = contentDomain.getCategories();

        contentDao.addArticle(contentDomain);

        int cid = contentDomain.getCid();
        metaService.addMetas(cid, tags, Types.TAG.getType());
        metaService.addMetas(cid, categories, Types.CATEGORY.getType());
    }

    @Override
    @Transactional
    @CacheEvict(value = {"atricleCache", "atricleCaches"}, allEntries = true, beforeInvocation = true)
    public void deleteArticleById(Integer cid) {
        if (null == cid)
            throw BusinessException.withErrorCode(ErrorConstant.Common.PARAM_IS_EMPTY);
        contentDao.deleteArticleById(cid);
        //同时也要删除该文章下的所有评论
        List<Comment> comments = commentDao.getCommentsByCId(cid);
        if (null != comments && comments.size() > 0) {
            comments.forEach(comment -> {
                commentDao.deleteComment(comment.getCoid());
            });
        }
        //删除标签和分类关联
        List<Relationship> relationships = relationshipService.getRelationshipByCid(cid);
        if (null != relationships && relationships.size() > 0) {
            relationshipService.deleteRelationshipByCid(cid);
        }

    }

    @Override
    @Transactional
    @CacheEvict(value = {"atricleCache", "atricleCaches"}, allEntries = true, beforeInvocation = true)
    public void updateArticleById(Content contentDomain) {
        //标签和分类
        String tags = contentDomain.getTags();
        String categories = contentDomain.getCategories();

        contentDao.updateArticleById(contentDomain);
        int cid = contentDomain.getCid();
        relationshipService.deleteRelationshipByCid(cid);
        metaService.addMetas(cid, tags, Types.TAG.getType());
        metaService.addMetas(cid, categories, Types.CATEGORY.getType());

    }

    @Override
    @Transactional
    @CacheEvict(value = {"atricleCache", "atricleCaches"}, allEntries = true, beforeInvocation = true)
    public void updateCategory(String ordinal, String newCatefory) {
        ContentCond cond = new ContentCond();
        cond.setCategory(ordinal);
        List<Content> atricles = contentDao.getArticlesByCond(cond);
        atricles.forEach(atricle -> {
            atricle.setCategories(atricle.getCategories().replace(ordinal, newCatefory));
            contentDao.updateArticleById(atricle);
        });
    }


    @Override
    @CacheEvict(value = {"atricleCache", "atricleCaches"}, allEntries = true, beforeInvocation = true)
    public void updateContentByCid(Content content) {
        if (null != content && null != content.getCid()) {
            contentDao.updateArticleById(content);
        }
    }

    @Override
    @Cacheable(value = "atricleCache", key = "'atricleById_' + #p0")
    public Content getAtricleById(Integer cid) {
        if (null == cid)
            throw BusinessException.withErrorCode(ErrorConstant.Common.PARAM_IS_EMPTY);
        return contentDao.getArticleById(cid);
    }

    @Override
    @Cacheable(value = "atricleCaches", key = "'articlesByCond_' + #p1 + 'type_' + #p0.type")
    public PageInfo<Content> getArticlesByCond(ContentCond contentCond, int pageNum, int pageSize) {
        if (null == contentCond)
            throw BusinessException.withErrorCode(ErrorConstant.Common.PARAM_IS_EMPTY);
        PageHelper.startPage(pageNum, pageSize);
        List<Content> contents = contentDao.getArticlesByCond(contentCond);
        PageInfo<Content> pageInfo = new PageInfo<>(contents);
        return pageInfo;
    }

    @Override
    @Cacheable(value = "atricleCaches", key = "'recentlyArticle_' + #p0")
    public PageInfo<Content> getRecentlyArticle(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Content> recentlyArticle = contentDao.getRecentlyArticle();
        PageInfo<Content> pageInfo = new PageInfo<>(recentlyArticle);
        return pageInfo;
    }

    @Override
    public PageInfo<Content> searchArticle(String param, int pageNun, int pageSize) {
        PageHelper.startPage(pageNun, pageSize);
        List<Content> contentDomains = contentDao.searchArticle(param);
        PageInfo<Content> pageInfo = new PageInfo<>(contentDomains);
        return pageInfo;
    }
}
