package com.mysite.controller.admin;

import com.github.pagehelper.PageInfo;
import com.mysite.constant.Types;
import com.mysite.constant.WebConst;
import com.mysite.model.dto.AttachDto;
import com.mysite.service.AttachService;
import com.mysite.utils.Commons;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Api("附件相关接口")
@Controller
@RequestMapping("admin/attach")
public class AttachController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AttachController.class);

    @Autowired
    private AttachService attachService;


    @ApiOperation("文件管理首页")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "page", value = "页数", paramType = "form", dataType = "int"),
            @ApiImplicitParam(name = "limit", value = "每页数量", paramType = "form", dataType = "int")
    })
    @GetMapping(value = "")
    public String index(
            @RequestParam(name = "page", required = false, defaultValue = "1") int page,
            @RequestParam(name = "limit", required = false, defaultValue = "12") int limit,
            HttpServletRequest request
    ) {
        PageInfo<AttachDto> atts = attachService.getAtts(page, limit);
        request.setAttribute("attachs", atts);
        request.setAttribute(Types.ATTACH_URL.getType(), Commons.site_option(Types.ATTACH_URL.getType(), Commons.site_url()));
        request.setAttribute("max_file_size", WebConst.MAX_FILE_SIZE / 1024);
        return "admin/attach";
    }


}
