package com.mysite.service.impl;

import com.mysite.dao.RelationshipDao;
import com.mysite.model.po.Relationship;
import com.mysite.service.RelationshipService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Donghua.Chen on 2018/4/30.
 */
@Service("relationshipService")
public class RelationshipServiceImpl implements RelationshipService {

    @Resource
    private RelationshipDao relationshipDao;

    /**
     * 添加
     *
     * @param relationship
     * @return
     */
    public int addRelationship(Relationship relationship) {
        return relationshipDao.addRelationship(relationship);
    }

    /**
     * 根据文章编号和meta编号删除关联
     *
     * @param cid
     * @param mid
     * @return
     */
    public int deleteRelationshipById(Integer cid, Integer mid) {
        return relationshipDao.deleteRelationshipById(cid, mid);
    }

    /**
     * 根据文章编号删除关联
     *
     * @param cid
     * @return
     */
    public int deleteRelationshipByCid(Integer cid) {
        return relationshipDao.deleteRelationshipByCid(cid);
    }

    /**
     * 根据meta编号删除关联
     *
     * @param mid
     * @return
     */
    public int deleteRelationshipByMid(Integer mid) {
        return relationshipDao.deleteRelationshipByMid(mid);
    }

    /**
     * 更新
     *
     * @param relationship
     * @return
     */
    public int updateRelationship(Relationship relationship) {
        return relationshipDao.updateRelationship(relationship);
    }

    /**
     * 根据文章主键获取关联
     *
     * @param cid
     * @return
     */
    public List<Relationship> getRelationshipByCid(Integer cid) {
        return relationshipDao.getRelationshipByCid(cid);
    }

    /**
     * 根据meta编号获取关联
     *
     * @param mid
     * @return
     */
    public List<Relationship> getRelationshipByMid(Integer mid) {
        return relationshipDao.getRelationshipByMid(mid);
    }

    /**
     * 获取数量
     *
     * @param cid
     * @param mid
     * @return
     */
    public Long getCountById(Integer cid, Integer mid) {
        return relationshipDao.getCountById(cid, mid);
    }


}
