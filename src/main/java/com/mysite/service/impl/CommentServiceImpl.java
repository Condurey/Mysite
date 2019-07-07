package com.mysite.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mysite.constant.ErrorConstant;
import com.mysite.dao.CommentDao;
import com.mysite.exception.BusinessException;
import com.mysite.model.dto.cond.CommentCond;
import com.mysite.model.entity.Comment;
import com.mysite.model.entity.Content;
import com.mysite.service.CommentService;
import com.mysite.service.ContentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 评论实现类
 * Created by Donghua.Chen on 2018/4/29.
 */
@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentDao commentDao;

    @Autowired
    private ContentService contentService;


    private static final Map<String, String> STATUS_MAP = new ConcurrentHashMap<>();

    /**
     * 评论状态：正常
     */
    private static final String STATUS_NORMAL = "approved";
    /**
     * 评论状态：不显示
     */
    private static final String STATUS_BLANK = "not_audit";

    static {
        STATUS_MAP.put("approved", STATUS_NORMAL);
        STATUS_MAP.put("not_audit", STATUS_BLANK);
    }

    @Override
    @Transactional
    @CacheEvict(value = "commentCache", allEntries = true)
    public void addComment(Comment Comment) {
        String msg = null;
        if (null == Comment) {
            msg = "评论对象为空";
        }
        if (StringUtils.isBlank(Comment.getAuthor())) {
            Comment.setAuthor("热心网友");
        }
//        if (StringUtils.isNotBlank(Comment.getMail()) && !TaleUtils.isEmail(Comment.getMail())) {
        if (StringUtils.isNotBlank(Comment.getMail())) {
            msg = "请输入正确的邮箱格式";
        }
        if (StringUtils.isBlank(Comment.getContent())) {
            msg = "评论内容不能为空";
        }
        if (Comment.getContent().length() < 5 || Comment.getContent().length() > 2000) {
            msg = "评论字数在5-2000个字符";
        }
        if (null == Comment.getCid()) {
            msg = "评论文章不能为空";
        }
        if (msg != null)
            throw BusinessException.withErrorCode(msg);
        Content atricle = contentService.getAtricleById(Comment.getCid());
        if (null == atricle)
            throw BusinessException.withErrorCode("该文章不存在");
        Comment.setOwnerId(atricle.getAuthorId());
        Comment.setStatus(STATUS_MAP.get(STATUS_BLANK));
//        Comment.setCreated(DateKit.getCurrentUnixTime());
        commentDao.addComment(Comment);

        Content temp = new Content();
        temp.setCid(atricle.getCid());
        Integer count = atricle.getCommentsNum();
        if (null == count) {
            count = 0;
        }
        temp.setCommentsNum(count + 1);
        contentService.updateContentByCid(temp);

    }

    @Transactional
    @Override
    @CacheEvict(value = "commentCache", allEntries = true)
    public void deleteComment(Integer coid) {
        if (null == coid)
            throw BusinessException.withErrorCode(ErrorConstant.Common.PARAM_IS_EMPTY);
        // 如果删除的评论存在子评论，一并删除
        //查找当前评论是否有子评论
        CommentCond commentCond = new CommentCond();
        commentCond.setParent(coid);
        Comment comment = commentDao.getCommentById(coid);
        List<Comment> childComments = commentDao.getCommentsByCond(commentCond);
        Integer count = 0;
        //删除子评论
        if (null != childComments && childComments.size() > 0) {
            for (int i = 0; i < childComments.size(); i++) {
                commentDao.deleteComment(childComments.get(i).getCoid());
                count++;
            }
        }
        //删除当前评论
        commentDao.deleteComment(coid);
        count++;

        //更新当前文章的评论数
        Content contentDomain = contentService.getAtricleById(comment.getCid());
        if (null != contentDomain
                && null != contentDomain.getCommentsNum()
                && contentDomain.getCommentsNum() != 0) {
            contentDomain.setCommentsNum(contentDomain.getCommentsNum() - count);
            contentService.updateContentByCid(contentDomain);
        }
    }

    @Override
    @CacheEvict(value = "commentCache", allEntries = true)
    public void updateCommentStatus(Integer coid, String status) {
        if (null == coid)
            throw BusinessException.withErrorCode(ErrorConstant.Common.PARAM_IS_EMPTY);
        commentDao.updateCommentStatus(coid, status);
    }

    @Override
    @Cacheable(value = "commentCache", key = "'commentById_' + #p0")
    public Comment getCommentById(Integer coid) {
        if (null == coid)
            throw BusinessException.withErrorCode(ErrorConstant.Common.PARAM_IS_EMPTY);

        return commentDao.getCommentById(coid);
    }

    @Override
    @Cacheable(value = "commentCache", key = "'commentsByCId_' + #p0")
    public List<Comment> getCommentsByCId(Integer cid) {
        if (null == cid)
            throw BusinessException.withErrorCode(ErrorConstant.Common.PARAM_IS_EMPTY);
        return commentDao.getCommentsByCId(cid);
    }

    @Override
    @Cacheable(value = "commentCache", key = "'commentsByCond_' + #p1")
    public PageInfo<Comment> getCommentsByCond(CommentCond commentCond, int pageNum, int pageSize) {
        if (null == commentCond)
            throw BusinessException.withErrorCode(ErrorConstant.Common.PARAM_IS_EMPTY);
        PageHelper.startPage(pageNum, pageSize);
        List<Comment> comments = commentDao.getCommentsByCond(commentCond);
        PageInfo<Comment> pageInfo = new PageInfo<>(comments);
        return pageInfo;
    }
}
