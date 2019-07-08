package com.mysite.service.impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mysite.constant.ErrorConstant;
import com.mysite.dao.AttachDao;
import com.mysite.exception.BusinessException;
import com.mysite.model.dto.AttachDto;
import com.mysite.model.entity.Attach;
import com.mysite.service.AttachService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 附件服务实现层
 * Created by Donghua.Chen on 2018/4/29.
 */
@Service("attachService")
public class AttachServiceImpl implements AttachService {

    @Autowired
    private AttachDao attachDao;

    @Override
    @CacheEvict(value = {"attCaches", "attCache"}, allEntries = true, beforeInvocation = true)
    public void addAttach(Attach attach) {
        if (null == attach)
            throw BusinessException.withErrorCode(ErrorConstant.Common.PARAM_IS_EMPTY);
        attachDao.addAttach(attach);

    }

    @Override
    @CacheEvict(value = {"attCaches", "attCache"}, allEntries = true, beforeInvocation = true)
    public void batchAddAttach(List<Attach> list) {
        if (null == list || list.size() == 0)
            throw BusinessException.withErrorCode(ErrorConstant.Common.PARAM_IS_EMPTY);
        attachDao.batchAddAttach(list);

    }

    @Override
    @CacheEvict(value = {"attCaches", "attCache"}, allEntries = true, beforeInvocation = true)
    public void deleteAttach(Integer id) {
        if (null == id)
            throw BusinessException.withErrorCode(ErrorConstant.Common.PARAM_IS_EMPTY);
        attachDao.deleteAttach(id);

    }

    @Override
    @CacheEvict(value = {"attCaches", "attCache"}, allEntries = true, beforeInvocation = true)
    public void updateAttach(Attach attach) {
        if (null == attach || null == attach.getId())
            throw BusinessException.withErrorCode(ErrorConstant.Common.PARAM_IS_EMPTY);
        attachDao.updateAttach(attach);

    }

    @Override
    @Cacheable(value = "attCache", key = "'attachById' + #p0")
    public AttachDto getAttachById(Integer id) {
        if (null == id)
            throw BusinessException.withErrorCode(ErrorConstant.Common.PARAM_IS_EMPTY);
        return attachDao.getAttachById(id);
    }

    @Override
    @Cacheable(value = "attCaches", key = "'atts' + #p0")
    public PageInfo<AttachDto> getAtts(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<AttachDto> atts = attachDao.getAtts();
        PageInfo<AttachDto> pageInfo = new PageInfo<>(atts);
        return pageInfo;
    }


}
