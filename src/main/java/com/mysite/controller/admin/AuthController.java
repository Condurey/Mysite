package com.mysite.controller.admin;

import com.mysite.constant.LogActions;
import com.mysite.constant.WebConst;
import com.mysite.exception.BusinessException;
import com.mysite.interceptor.AuthService;
import com.mysite.model.po.User;
import com.mysite.service.LogService;
import com.mysite.service.UserService;
import com.mysite.utils.APIResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Api("登录相关接口")
@Controller
@RequestMapping(value = "/admin")
public class AuthController {

    private static final Logger LOGGER = LogManager.getLogger(AuthController.class);
    @Autowired
    private UserService userService;

    @Autowired
    private AuthService authService;

    @Autowired
    private LogService logService;

    @ApiOperation("跳转登录页")
    @GetMapping(value = "/login")
    public String login() {
        return "admin/login";
    }

    @ApiOperation("登录")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "username", value = "用户名", paramType = "form", required = true),
            @ApiImplicitParam(name = "password", value = "密码", paramType = "form", required = true),
            @ApiImplicitParam(name = "remeber_me", value = "记住我", paramType = "form")
    })
    @PostMapping(value = "/login")
    @ResponseBody
    public APIResponse toLogin(@RequestParam(name = "username") String username,
                               @RequestParam(name = "password") String password,
                               @RequestParam(name = "remeber_me", required = false) String remeber_me,
                               HttpServletRequest request,
                               HttpServletResponse response
    ) {
//        Integer error_count = cache.get("login_error_count");
        try {
            User userInfo = userService.login(username, password);
            request.getSession().setAttribute(WebConst.LOGIN_SESSION_KEY, userInfo);
            if (StringUtils.isNotBlank(remeber_me)) {
                authService.setCookie(response, userInfo.getUid());
            }
            logService.addLog(LogActions.LOGIN.getAction(), null, request.getRemoteAddr(), userInfo.getUid());
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
//            error_count = null == error_count ? 1 : error_count + 1;
//            if (error_count > 3) {
//                return APIResponse.fail("您输入密码已经错误超过3次，请10分钟后尝试");
//            }
//            cache.set("login_error_count", error_count, 10 * 60);
            String msg = "登录失败";
            if (e instanceof BusinessException) {
                msg = e.getMessage();
            } else {
                LOGGER.error(msg, e);
            }
            return APIResponse.fail(msg);
        }

        return APIResponse.success();

    }


    /**
     * 退出登录
     *
     * @param session
     * @param response
     */
    @RequestMapping("/logout")
    public void logout(HttpSession session, HttpServletResponse response, HttpServletRequest request) {
        session.removeAttribute(WebConst.LOGIN_SESSION_KEY);
        Cookie cookie = new Cookie(WebConst.USER_IN_COOKIE, "");
        cookie.setValue(null);
        cookie.setMaxAge(0);// 立即销毁cookie
        cookie.setPath("/");
        response.addCookie(cookie);
        try {
            response.sendRedirect("/admin/login");
        } catch (IOException e) {
            e.printStackTrace();
            LOGGER.error("退出登录失败", e);
        }
    }
}
