package com.mysite.controller.admin;

import com.github.pagehelper.PageInfo;
import com.mysite.constant.WebConst;
import com.mysite.model.dto.StatisticsDto;
import com.mysite.model.po.Comment;
import com.mysite.model.po.Content;
import com.mysite.model.po.Log;
import com.mysite.model.po.User;
import com.mysite.service.LogService;
import com.mysite.service.SiteService;
import com.mysite.service.UserService;
import com.mysite.utils.APIResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 后台管理首页
 * Created by Administrator on 2017/3/9 009.
 */
@Api("后台首页")
@Controller("adminIndexController")
@RequestMapping(value = "/admin")
public class IndexController {
    private static final Logger LOGGER = LoggerFactory.getLogger(IndexController.class);

    @Autowired
    private SiteService siteService;

    @Autowired
    private LogService logService;

    @Autowired
    private UserService userService;

    @ApiOperation("进入首页")
    @GetMapping(value = {"", "/index"})
    public String index(HttpServletRequest request) {
        LOGGER.info("Enter admin index method");
        List<Comment> comments = siteService.getComments(5);
        List<Content> contents = siteService.getNewArticles(5);
        StatisticsDto statistics = siteService.getStatistics();
        // 取最新的20条日志
        PageInfo<Log> logs = logService.getLogs(1, 5);
        List<Log> list = logs.getList();
        request.setAttribute("comments", comments);
        request.setAttribute("articles", contents);
        request.setAttribute("statistics", statistics);
        request.setAttribute("logs", list);
        LOGGER.info("Exit admin index method");
        return "admin/index";
    }

    /**
     * 个人设置页面
     */
    @GetMapping(value = "profile")
    public String profile() {
        return "admin/profile";
    }


    /**
     * 保存个人信息
     */
    @PostMapping(value = "/profile")
    @ResponseBody
    public APIResponse saveProfile(@RequestParam String screenName, @RequestParam String email, HttpServletRequest request, HttpSession session) {
//        User users = this.user(request);
        if (StringUtils.isNotBlank(screenName) && StringUtils.isNotBlank(email)) {
            User temp = new User();
//            temp.setUid(users.getUid());
            temp.setScreenName(screenName);
            temp.setEmail(email);
            userService.updateUserInfo(temp);
//            logService.addLog(LogActions.UP_INFO.getAction(), GsonUtils.toJsonString(temp), request.getRemoteAddr(), this.getUid(request));

            //更新session中的数据
            User original = (User) session.getAttribute(WebConst.LOGIN_SESSION_KEY);
            original.setScreenName(screenName);
            original.setEmail(email);
            session.setAttribute(WebConst.LOGIN_SESSION_KEY, original);
        }
        return APIResponse.success();
    }



}
