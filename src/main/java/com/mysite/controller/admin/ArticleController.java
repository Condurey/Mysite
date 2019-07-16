package com.mysite.controller.admin;

import com.github.pagehelper.PageInfo;
import com.mysite.constant.Types;
import com.mysite.exception.BusinessException;
import com.mysite.model.po.Content;
import com.mysite.model.po.Meta;
import com.mysite.model.query.ContentQuery;
import com.mysite.model.query.MetaQuery;
import com.mysite.service.ContentService;
import com.mysite.service.LogService;
import com.mysite.service.MetaService;
import com.mysite.utils.APIResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

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

    @Autowired
    private LogService logService;

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

    @ApiOperation("发布新文章")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "title", value = "标题", paramType = "form", dataType = "String", required = true),
            @ApiImplicitParam(name = "titlePic", value = "标题图片", paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "slug", value = "内容缩略名", paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "content", value = "内容", paramType = "form", dataType = "String", required = true),
            @ApiImplicitParam(name = "type", value = "文章类型", paramType = "form", dataType = "String", required = true),
            @ApiImplicitParam(name = "status", value = "文章状态", paramType = "form", dataType = "String", required = true),
            @ApiImplicitParam(name = "tags", value = "标签", paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "categories", value = "分类", paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "allowComment", value = "是否允许评论", paramType = "form", dataType = "boolean", required = true)
    })
    @PostMapping(value = "/publish")
    @ResponseBody
    public APIResponse publishArticle(@RequestParam(name = "title") String title,
                                      @RequestParam(name = "titlePic", required = false) String titlePic,
                                      @RequestParam(name = "slug", required = false) String slug,
                                      @RequestParam(name = "content") String content,
                                      @RequestParam(name = "type") String type,
                                      @RequestParam(name = "status") String status,
                                      @RequestParam(name = "tags", required = false) String tags,
                                      @RequestParam(name = "categories", required = false, defaultValue = "默认分类") String categories,
                                      @RequestParam(name = "allowComment") Boolean allowComment,
                                      HttpServletRequest request
    ) {
        Content article = new Content();
        article.setTitle(title);
        article.setTitlePic(titlePic);
        article.setSlug(slug);
        article.setContent(content);
        article.setType(type);
        article.setStatus(status);
        article.setTags(type.equals(Types.ARTICLE.getType()) ? tags : null);
        //只允许博客文章有分类，防止作品被收入分类
        article.setCategories(type.equals(Types.ARTICLE.getType()) ? categories : null);
        article.setAllowComment(allowComment);

        contentService.addArticle(article);

        return APIResponse.success();


    }

    @ApiOperation("文章编辑页")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "cid", value = "文章编号", paramType = "path", dataType = "int", required = true)
    })
    @GetMapping(value = "/{cid}")
    public String editArticle(@PathVariable Integer cid,
                              HttpServletRequest request
    ) {
        Content content = contentService.getAtricleById(cid);
        request.setAttribute("contents", content);
        MetaQuery metaCond = new MetaQuery();
        metaCond.setType(Types.CATEGORY.getType());
        List<Meta> categories = metaService.getMetas(metaCond);
        request.setAttribute("categories", categories);
        request.setAttribute("active", "article");
        return "admin/article_edit";
    }

    @ApiOperation("编辑保存文章")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "cid", value = "文章主键", paramType = "form", dataType = "int", required = true),
            @ApiImplicitParam(name = "title", value = "标题", paramType = "form", dataType = "String", required = true),
            @ApiImplicitParam(name = "titlePic", value = "标题图片", paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "slug", value = "内容缩略名", paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "content", value = "内容", paramType = "form", dataType = "String", required = true),
            @ApiImplicitParam(name = "type", value = "文章类型", paramType = "form", dataType = "String", required = true),
            @ApiImplicitParam(name = "status", value = "文章状态", paramType = "form", dataType = "String", required = true),
            @ApiImplicitParam(name = "tags", value = "标签", paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "categories", value = "分类", paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "allowComment", value = "是否允许评论", paramType = "form", dataType = "boolean", required = true)
    })
    @PostMapping("/modify")
    @ResponseBody
    public APIResponse modifyArticle(@RequestParam(name = "cid") Integer cid,
                                     @RequestParam(name = "title") String title,
                                     @RequestParam(name = "titlePic", required = false) String titlePic,
                                     @RequestParam(name = "slug", required = false) String slug,
                                     @RequestParam(name = "content") String content,
                                     @RequestParam(name = "type") String type,
                                     @RequestParam(name = "status") String status,
                                     @RequestParam(name = "tags", required = false) String tags,
                                     @RequestParam(name = "categories", required = false, defaultValue = "默认分类") String categories,
                                     @RequestParam(name = "allowComment") Boolean allowComment,
                                     HttpServletRequest request
    ) {
        Content article = new Content();
        article.setCid(cid);
        article.setTitle(title);
        article.setTitlePic(titlePic);
        article.setSlug(slug);
        article.setContent(content);
        article.setType(type);
        article.setStatus(status);
        article.setTags(tags);
        article.setCategories(categories);
        article.setAllowComment(allowComment);

        contentService.updateArticleById(article);
        return APIResponse.success();
    }

    @ApiOperation("删除文章")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "cid", value = "文章编号", paramType = "form", dataType = "int", required = true)
    })
    @PostMapping(value = "/delete")
    @ResponseBody
    public APIResponse deleteArticle(@RequestParam(name = "cid") Integer cid,
                                     HttpServletRequest request
    ) {
        contentService.deleteArticleById(cid);
//        logService.addLog(LogActions.DEL_ARTICLE.getAction(), cid + "", request.getRemoteAddr(), this.getUid(request));
        return APIResponse.success();
    }


}
