package com.cdr.dao;

import com.cdr.model.Attachment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by Donghua.Chen on 2018/4/29.
 */
@Mapper
public interface AttachmentDao {


    /**
     * 添加单个附件信息
     * @param attachment
     * @return
     */
    int addAttachment(Attachment attachment);

    /**
     * 批量添加附件信息
     * @param list
     * @return
     */
    int batchAddAttachment(List<Attachment> list);

    /**
     * 根据主键编号删除附件信息
     * @param id
     * @return
     */
    int deleteAttachment(int id);

    /**
     * 更新附件信息
     * @param attachment
     * @return
     */
    int updateAttachment(Attachment attachment);

    /**
     * 根据主键获取附件信息
     * @param id
     * @return
     */
    Attachment getAttachmentById(@Param("id") int id);

    /**
     * 获取所有的附件信息
     * @return
     */
    List<Attachment> getAttachments();

    /**
     * 查找附件的数量
     * @return
     */
    Long getAttachmentsCount();
}
