package com.mysite.service;

import com.mysite.model.entity.Relationship;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 关联关系
 * Created by Donghua.Chen on 2018/4/30.
 */
public interface RelationshipService {
    /**
     * 添加
     *
     * @param relationship
     * @return
     */
    int addRelationship(Relationship relationship);

    /**
     * 根据文章编号和meta编号删除关联
     *
     * @param cid
     * @param mid
     * @return
     */
    int deleteRelationshipById(@Param("cid") Integer cid, @Param("mid") Integer mid);

    /**
     * 根据文章编号删除关联
     *
     * @param cid
     * @return
     */
    int deleteRelationshipByCid(@Param("cid") Integer cid);

    /**
     * 根据meta编号删除关联
     *
     * @param mid
     * @return
     */
    int deleteRelationshipByMid(@Param("mid") Integer mid);

    /**
     * 更新
     *
     * @param relationship
     * @return
     */
    int updateRelationship(Relationship relationship);

    /**
     * 根据文章主键获取关联
     *
     * @param cid
     * @return
     */
    List<Relationship> getRelationshipByCid(@Param("cid") Integer cid);

    /**
     * 根据meta编号获取关联
     *
     * @param mid
     * @return
     */
    List<Relationship> getRelationshipByMid(@Param("mid") Integer mid);

    /**
     * 获取数量
     *
     * @param cid
     * @param mid
     * @return
     */
    Long getCountById(@Param("cid") Integer cid, @Param("mid") Integer mid);

}
