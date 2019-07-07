package com.mysite.controller.admin;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Api("登录相关接口")
@Controller
@RequestMapping(value = "/admin")
public class AuthController {

    private static final Logger LOGGER = LogManager.getLogger(AuthController.class);

    @ApiOperation("跳转登录页")
    @GetMapping(value = "/login")
    public String login() {
        return "admin/login";
    }
}
