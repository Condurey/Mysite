package com.mysite.service;

import com.mysite.model.dto.MetaDto;
import com.mysite.model.po.Meta;
import com.mysite.model.query.MetaQuery;

import java.util.List;

/**
 * 项目服务层
 * Created by Donghua.Chen on 2018/4/29.
 */
public interface MetaService {
    /**
     * 添加项目
     *
     * @param meta
     * @return
     */
    void addMeta(Meta meta);

    /**
     * 添加
     *
     * @param type
     * @param name
     * @param mid
     */
    void saveMeta(String type, String name, Integer mid);


    /**
     * 批量添加
     *
     * @param cid
     * @param names
     * @param type
     */
    void addMetas(Integer cid, String names, String type);


    /**
     * 添加或者更新
     *
     * @param cid
     * @param name
     * @param type
     */
    void saveOrUpdate(Integer cid, String name, String type);

    /**
     * 删除项目
     *
     * @param mid
     * @return
     */
    void deleteMetaById(Integer mid);

    /**
     * 更新项目
     *
     * @param meta
     * @return
     */
    void updateMeta(Meta meta);

    /**
     * 根据编号获取项目
     *
     * @param mid
     * @return
     */
    Meta getMetaById(Integer mid);

    /**
     * 根据查询条件获取项目
     *
     * @param metaQuery
     * @return
     */
    MetaDto getMetaByQuery(MetaQuery metaQuery);

    /**
     * 获取所有的项目
     *
     * @param metaQuery 查询条件
     * @return
     */
    List<Meta> getMetas(MetaQuery metaQuery);

    /**
     * 根据类型查询项目列表，带项目下面的文章数
     *
     * @param type
     * @param orderby
     * @param limit
     * @return
     */
    List<MetaDto> getMetaList(String type, String orderby, int limit);
}
