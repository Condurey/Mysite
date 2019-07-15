package com.mysite.controller.admin;

import com.github.pagehelper.PageInfo;
import com.mysite.constant.Types;
import com.mysite.exception.BusinessException;
import com.mysite.model.po.Content;
import com.mysite.model.po.Meta;
import com.mysite.model.query.ContentQuery;
import com.mysite.model.query.MetaQuery;
import com.mysite.service.ContentService;
import com.mysite.service.MetaService;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Api("文章管理")
@Controller
@RequestMapping("/admin/article")
@Transactional(rollbackFor = BusinessException.class)
public class ArticleController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ArticleController.class);
    @Autowired
    private ContentService contentService;

    @Autowired
    private MetaService metaService;

    @ApiOperation("文章页")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "page", value = "页数", paramType = "form", dataType = "int"),
            @ApiImplicitParam(name = "limit", value = "每页数量", paramType = "form", dataType = "int")
    })
    @GetMapping(value = "")
    public String index(
            @RequestParam(name = "page", required = false, defaultValue = "1") int page,
            @RequestParam(name = "limit", required = false, defaultValue = "15") int limit,
            HttpServletRequest request
    ) {
        PageInfo<Content> articles = contentService.getArticlesByCond(new ContentQuery(), page, limit);
        request.setAttribute("articles", articles);
        return "admin/article_list";
    }

    /**
     * 文章发表
     *
     * @param request
     * @return
     */
    @ApiOperation("发布文章页")
    @GetMapping(value = "/publish")
    public String newArticle(HttpServletRequest request) {
        MetaQuery metaQuery = new MetaQuery();
        metaQuery.setType(Types.CATEGORY.getType());
        List<Meta> metaList = metaService.getMetas(metaQuery);
        request.setAttribute("categories", metaList);
        return "admin/article_edit";
    }
}
