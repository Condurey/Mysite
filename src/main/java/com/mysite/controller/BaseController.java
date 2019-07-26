package com.mysite.controller;


import com.mysite.constant.Types;
import com.mysite.constant.WebConst;
import com.mysite.interceptor.AuthService;
import com.mysite.model.dto.MetaDto;
import com.mysite.model.po.User;
import com.mysite.service.MetaService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by Donghua.Chen on 2018/4/30.
 */
public abstract class BaseController {

    @Autowired
    private MetaService metaService;

    @Autowired
    private AuthService authService;


//    protected MapCache cache = MapCache.single();

    public BaseController title(HttpServletRequest request, String title) {
        request.setAttribute("title", title);
        return this;
    }

    /**
     * 获取blog页面需要的公共数据
     *
     * @param request
     * @return
     */
    public BaseController blogBaseData(HttpServletRequest request) {

        List<MetaDto> links = metaService.getMetaList(Types.LINK.getType(), null, WebConst.MAX_POSTS);
        request.setAttribute("links", links);
        return this;
    }

    /**
     * 获取请求绑定的登录对象
     *
     * @param request
     * @return
     */
    public User user(HttpServletRequest request) {
        return authService.getLoginUser(request);
    }

    public Integer getUid(HttpServletRequest request) {
        return this.user(request).getUid();
    }

}
