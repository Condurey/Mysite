package com.cdr.dao;

import com.cdr.model.Options;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 网站配置dao
 * Created by Donghua.Chen on 2018/4/29.
 */
@Mapper
public interface OptionDao {

    /**
     * 删除网站配置
     * @param name
     * @return
     */
    int deleteOptionByName(@Param("name") String name);

    /**
     * 更新网站配置
     * @param options
     * @return
     */
    int updateOptionByName(Options options);

    /***
     * 根据名称获取网站配置
     * @param name
     * @return
     */
    Options getOptionByName(@Param("name") String name);

    /**
     * 获取全部网站配置
     * @return
     */
    List<Options> getOptions();
}
