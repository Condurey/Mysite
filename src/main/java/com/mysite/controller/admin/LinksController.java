package com.mysite.controller.admin;

import com.mysite.constant.Types;
import com.mysite.model.po.Meta;
import com.mysite.model.query.MetaQuery;
import com.mysite.service.MetaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@Api("友链")
@Controller
@RequestMapping(value = "admin/links")
public class LinksController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LinksController.class);

    @Autowired
    private MetaService metaService;

    @ApiOperation("友链页面")
    @GetMapping(value = "")
    public String index(HttpServletRequest request) {

        MetaQuery metaCond = new MetaQuery();
        metaCond.setType(Types.LINK.getType());
        List<Meta> metas = metaService.getMetas(metaCond);
        request.setAttribute("links", metas);
        return "admin/links";
    }


}
