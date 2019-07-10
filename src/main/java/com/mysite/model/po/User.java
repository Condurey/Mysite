package com.mysite.model.po;

import lombok.Data;

import java.io.Serializable;

/**
 * @author
 */
@Data
public class User implements Serializable {

    private static final long serialVersionUID = -5179669569592130573L;
    /**
     * user表主键
     */
    private Integer uid;

    /**
     * 用户名称
     */
    private String username;

    /**
     * 用户密码
     */
    private String password;

    /**
     * 用户的邮箱
     */
    private String email;

    /**
     * 用户的主页
     */
    private String homeUrl;

    /**
     * 用户显示的名称
     */
    private String screenName;

    /**
     * 用户注册时的GMT unix时间戳
     */
    private Integer created;

    /**
     * 最后活动时间
     */
    private Integer activated;

    /**
     * 上次登录最后活跃时间
     */
    private Integer logged;

    /**
     * 用户组
     */
    private String groupName;

}