package com.mysite.service;

import com.mysite.model.entity.User;
import org.springframework.stereotype.Service;

/**
 * Created by Donghua.Chen on 2018/4/20.
 */
@Service
public interface UserService {

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
    User getUserInfoById(Integer uId);


    /**
     * 用户登录
     *
     * @param username 用户名
     * @param password 密码
     * @return
     */
    User login(String username, String password);

}
