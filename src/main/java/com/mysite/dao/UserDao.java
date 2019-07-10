package com.mysite.dao;

import com.mysite.model.po.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * Created by Donghua.Chen on 2018/4/20.
 */
@Mapper
public interface UserDao {

    /**
     * @param user
     * @Author: Donghua.Chen
     * @Description: 更改用户信息
     * @Date: 2018/4/20
     */
    int updateUserInfo(User user);

    /**
     * @param uId 主键
     * @Author: Donghua.Chen
     * @Description: 根据主键编号获取用户信息
     * @Date: 2018/4/20
     */
    User getUserInfoById(@Param("uid") Integer uId);

    /**
     * 根据用户名和密码获取用户信息
     *
     * @param username
     * @param password
     * @return
     */
    User getUserInfoByCond(@Param("username") String username, @Param("password") String password);

}
