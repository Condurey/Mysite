package com.mysite.service.impl;

import com.mysite.constant.ErrorConstant;
import com.mysite.constant.Types;
import com.mysite.constant.WebConst;
import com.mysite.dao.MetaDao;
import com.mysite.dao.RelationshipDao;
import com.mysite.exception.BusinessException;
import com.mysite.model.dto.MetaDto;
import com.mysite.model.po.Content;
import com.mysite.model.po.Meta;
import com.mysite.model.po.Relationship;
import com.mysite.model.query.MetaQuery;
import com.mysite.service.ContentService;
import com.mysite.service.MetaService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Donghua.Chen on 2018/4/29.
 */
@Service("metaService")
public class MetaServiceImpl implements MetaService {

    @Resource
    private MetaDao metaDao;

    @Resource
    private RelationshipDao relationshipDao;


    @Autowired
    private ContentService contentService;

    @Override
    @Transactional
    @CacheEvict(value = {"metaCaches", "metaCache"}, allEntries = true, beforeInvocation = true)
    public void addMeta(Meta meta) {
        if (null == meta)
            throw BusinessException.withErrorCode(ErrorConstant.Common.PARAM_IS_EMPTY);
        metaDao.addMeta(meta);

    }

    @Override
    @CacheEvict(value = {"metaCaches", "metaCache"}, allEntries = true, beforeInvocation = true)
    public void saveMeta(String type, String name, Integer mid) {
        if (StringUtils.isNotBlank(type) && StringUtils.isNotBlank(name)) {
            MetaQuery metaQuery = new MetaQuery();
            metaQuery.setName(name);
            metaQuery.setType(type);
            List<Meta> metas = metaDao.getMetasByCond(metaQuery);
            if (null == metas || metas.size() == 0) {
                Meta meta = new Meta();
                meta.setName(name);
                if (null != mid) {
                    Meta oldMeta = metaDao.getMetaById(mid);
                    if (null != oldMeta)
                        meta.setMid(mid);

                    metaDao.updateMeta(meta);
                    //更新原有的文章分类
                    contentService.updateCategory(oldMeta.getName(), name);
                } else {
                    meta.setType(type);
                    metaDao.addMeta(meta);
                }
            } else {
                throw BusinessException.withErrorCode(ErrorConstant.Meta.META_IS_EXIST);

            }

        }
    }

    @Override
    @Transactional
    @CacheEvict(value = {"metaCaches", "metaCache"}, allEntries = true, beforeInvocation = true)
    public void addMetas(Integer cid, String names, String type) {
        if (null == cid)
            throw BusinessException.withErrorCode(ErrorConstant.Common.PARAM_IS_EMPTY);

        if (StringUtils.isNotBlank(names) && StringUtils.isNotBlank(type)) {
            String[] nameArr = StringUtils.split(names, ",");
            for (String name : nameArr) {
                this.saveOrUpdate(cid, name, type);
            }
        }
    }

    @Override
    @CacheEvict(value = {"metaCaches", "metaCache"}, allEntries = true, beforeInvocation = true)
    public void saveOrUpdate(Integer cid, String name, String type) {
        MetaQuery metaQuery = new MetaQuery();
        metaQuery.setName(name);
        metaQuery.setType(type);
        List<Meta> metas = this.getMetas(metaQuery);

        int mid;
        Meta metaDomain;
        if (metas.size() == 1) {
            Meta meta = metas.get(0);
            mid = meta.getMid();
        } else if (metas.size() > 1) {
            throw BusinessException.withErrorCode(ErrorConstant.Meta.NOT_ONE_RESULT);
        } else {
            metaDomain = new Meta();
            metaDomain.setSlug(name);
            metaDomain.setName(name);
            metaDomain.setType(type);
            this.addMeta(metaDomain);
            mid = metaDomain.getMid();
        }
        if (mid != 0) {
            Long count = relationshipDao.getCountById(cid, mid);
            if (count == 0) {
                Relationship relationship = new Relationship();
                relationship.setCid(cid);
                relationship.setMid(mid);
                relationshipDao.addRelationship(relationship);
            }

        }
    }

    @Override
    @Transactional
    @CacheEvict(value = {"metaCaches", "metaCache"}, allEntries = true, beforeInvocation = true)
    public void deleteMetaById(Integer mid) {
        if (null == mid)
            throw BusinessException.withErrorCode(ErrorConstant.Common.PARAM_IS_EMPTY);

        Meta meta = metaDao.getMetaById(mid);
        if (null != meta) {
            String type = meta.getType();
            String name = meta.getName();
            metaDao.deleteMetaById(mid);
            //需要把相关的数据删除
            List<Relationship> relationships = relationshipDao.getRelationshipByMid(mid);
            if (null != relationships && relationships.size() > 0) {
                for (Relationship relationship : relationships) {
                    Content article = contentService.getAtricleById(relationship.getCid());
                    if (null != article) {
                        Content temp = new Content();
                        temp.setCid(relationship.getCid());
                        if (type.equals(Types.CATEGORY.getType())) {
                            temp.setCategories(reMeta(name, article.getCategories()));
                        }
                        if (type.equals(Types.TAG.getType())) {
                            temp.setTags(reMeta(name, article.getTags()));
                        }
                        //将删除的资源去除
                        contentService.updateArticleById(temp);
                    }
                }
                relationshipDao.deleteRelationshipByMid(mid);
            }
        }


    }

    @Override
    @Transactional
    @CacheEvict(value = {"metaCaches", "metaCache"}, allEntries = true, beforeInvocation = true)
    public void updateMeta(Meta meta) {
        if (null == meta || null == meta.getMid())
            throw BusinessException.withErrorCode(ErrorConstant.Common.PARAM_IS_EMPTY);
        metaDao.updateMeta(meta);

    }

    @Override
    @Cacheable(value = "metaCache", key = "'metaById_' + #p0")
    public Meta getMetaById(Integer mid) {
        if (null == mid)
            throw BusinessException.withErrorCode(ErrorConstant.Common.PARAM_IS_EMPTY);
        return metaDao.getMetaById(mid);
    }

    @Override
    public MetaDto getMetaByQuery(MetaQuery metaQuery) {
        return metaDao.getMetaByQuery(metaQuery);
    }

    @Override
    @Cacheable(value = "metaCaches", key = "'metas_' + #p0")
    public List<Meta> getMetas(MetaQuery metaQuery) {
        return metaDao.getMetasByCond(metaQuery);
    }


    @Override
    @Cacheable(value = "metaCaches", key = "'metaList_' + #p0")
    public List<MetaDto> getMetaList(String type, String order, int limit) {
        if (StringUtils.isNotBlank(type)) {
            if (StringUtils.isBlank(order)) {
                order = "count desc, a.mid desc";
            }
            if (limit < 1 || limit > WebConst.MAX_POSTS) {
                limit = 10;
            }
            MetaQuery metaQuery = new MetaQuery();
            metaQuery.setType(type);
            metaQuery.setOrder(order);
            metaQuery.setLimit(limit);

            return metaDao.selectFromSql(metaQuery);
        }
        return null;
    }

    private String reMeta(String name, String metas) {
        String[] ms = StringUtils.split(metas, ",");
        StringBuilder sbuf = new StringBuilder();
        for (String m : ms) {
            if (!name.equals(m)) {
                sbuf.append(",").append(m);
            }
        }
        if (sbuf.length() > 0) {
            return sbuf.substring(1);
        }
        return "";
    }
}
