package com.mysite.dao;

import com.mysite.model.dto.AttachDto;
import com.mysite.model.entity.Attach;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by Donghua.Chen on 2018/4/29.
 */
@Mapper
public interface AttachDao {


    /**
     * 添加单个附件信息
     *
     * @param attach
     * @return
     */
    int addAttach(Attach attach);

    /**
     * 批量添加附件信息
     *
     * @param list
     * @return
     */
    int batchAddAttach(List<Attach> list);

    /**
     * 根据主键编号删除附件信息
     *
     * @param id
     * @return
     */
    int deleteAttach(int id);

    /**
     * 更新附件信息
     *
     * @param attach
     * @return
     */
    int updateAttach(Attach attach);

    /**
     * 根据主键获取附件信息
     *
     * @param id
     * @return
     */
    AttachDto getAttachById(@Param("id") int id);

    /**
     * 获取所有的附件信息
     *
     * @return
     */
    List<AttachDto> getAtts();

    /**
     * 查找附件的数量
     *
     * @return
     */
    Long getAttsCount();
}
