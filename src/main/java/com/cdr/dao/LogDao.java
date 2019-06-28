package com.cdr.dao;

import com.cdr.model.Log;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by Donghua.Chen on 2018/4/29.
 */
@Mapper
public interface LogDao {

    /**
     * 添加日志
     * @param log
     * @return
     */
    int addLog(Log log);

    /**
     * 删除日志
     * @param id
     * @return
     */
    int deleteLogById(@Param("id") Integer id);

    /**
     * 获取日志
     * @return
     */
    List<Log> getLogs();
}
