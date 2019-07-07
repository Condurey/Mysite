package com.mysite.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 后台管理首页
 * Created by Administrator on 2017/3/9 009.
 */
@Controller("adminIndexController")
@RequestMapping("/admin")
public class IndexController {

    /**
     * 个人设置页面
     */
    @GetMapping(value = "profile")
    public String profile() {
        return "admin/profile";
    }

    /**
     * admin 退出登录
     *
     * @return
     */
    @GetMapping(value = "logout")
    public String logout() {
        System.out.println("index-----------logout");
        return "admin/login";
    }

}
