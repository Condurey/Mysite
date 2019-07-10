package com.mysite.dao;

import com.mysite.model.dto.MetaDto;
import com.mysite.model.po.Meta;
import com.mysite.model.query.MetaQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 项目dao
 * Created by Donghua.Chen on 2018/4/29.
 */
@Mapper
public interface MetaDao {

    /**
     * 添加项目
     *
     * @param meta
     * @return
     */
    int addMeta(Meta meta);

    /**
     * 删除项目
     *
     * @param mid
     * @return
     */
    int deleteMetaById(@Param("mid") Integer mid);

    /**
     * 更新项目
     *
     * @param meta
     * @return
     */
    int updateMeta(Meta meta);

    /**
     * 根据编号获取项目
     *
     * @param mid
     * @return
     */
    Meta getMetaById(@Param("mid") Integer mid);


    /**
     * 根据条件查询
     *
     * @param metaQuery
     * @return
     */
    List<Meta> getMetasByCond(MetaQuery metaQuery);

    /**
     * 根据类型获取meta数量
     *
     * @param type
     * @return
     */
    Long getMetasCountByType(@Param("type") String type);

    /**
     * 根据sql查询
     *
     * @param paraMap
     * @return
     */
    List<MetaDto> selectFromSql(Map<String, Object> paraMap);

}
