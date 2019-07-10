package com.mysite.dao;

import com.mysite.model.po.Option;
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
     *
     * @param name
     * @return
     */
    int deleteOptionByName(@Param("name") String name);

    /**
     * 更新网站配置
     *
     * @param option
     * @return
     */
    int updateOptionByName(Option option);

    /***
     * 根据名称获取网站配置
     * @param name
     * @return
     */
    Option getOptionByName(@Param("name") String name);

    /**
     * 获取全部网站配置
     *
     * @return
     */
    List<Option> getOptions();
}
