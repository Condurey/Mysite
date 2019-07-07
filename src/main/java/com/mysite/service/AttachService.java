package com.mysite.service;

import com.github.pagehelper.PageInfo;
import com.mysite.model.dto.AttachDto;
import com.mysite.model.entity.Attach;

import java.util.List;

/**
 * 附件服务层
 * Created by Donghua.Chen on 2018/4/29.
 */
public interface AttachService {

    /**
     * 添加单个附件信息
     *
     * @param attach
     * @return
     */
    void addAttach(Attach attach);

    /**
     * 批量添加附件信息
     *
     * @param list
     * @return
     */
    void batchAddAttach(List<Attach> list);

    /**
     * 根据主键编号删除附件信息
     *
     * @param id
     * @return
     */
    void deleteAttach(Integer id);

    /**
     * 更新附件信息
     *
     * @param attach
     * @return
     */
    void updateAttach(Attach attach);

    /**
     * 根据主键获取附件信息
     *
     * @param id
     * @return
     */
    AttachDto getAttachById(Integer id);

    /**
     * 获取所有的附件信息
     *
     * @return
     */
    PageInfo<AttachDto> getAtts(int pageNum, int pageSize);
}
