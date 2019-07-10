package com.mysite.model.query;

import lombok.Data;

/**
 * 用户查找条件
 * Created by Donghua.Chen on 2018/4/30.
 */
@Data
public class UserQuery {
    private String username;
    private String password;
}
