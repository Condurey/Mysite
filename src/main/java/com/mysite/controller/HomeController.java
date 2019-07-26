package com.mysite.controller;


import com.github.pagehelper.PageInfo;
import com.mysite.constant.Types;
import com.mysite.constant.WebConst;
import com.mysite.model.dto.ArchiveDto;
import com.mysite.model.po.Comment;
import com.mysite.model.po.Content;
import com.mysite.model.query.ContentQuery;
import com.mysite.service.CommentService;
import com.mysite.service.ContentService;
import com.mysite.service.SiteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Api("网站首页和关于页面")
@Controller
public class HomeController extends BaseController {
    @Autowired
    private ContentService contentService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private SiteService siteService;


    @ApiOperation("blog首页")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "limit", value = "每页数量", paramType = "form", dataType = "int")
    })
    @GetMapping(value = {"/blog/", "/blog/index"})
    public String blogIndex(HttpServletRequest request,
                            @RequestParam(name = "limit", required = false, defaultValue = "11") int limit
    ) {
        return this.blogIndex(1, limit, request);
    }

    @ApiOperation("blog首页-分页")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "page", value = "页数", paramType = "path", dataType = "int"),
            @ApiImplicitParam(name = "limit", value = "每页数量", paramType = "form", dataType = "int")
    })
    @GetMapping(value = "/blog/page/{page}")
    public String blogIndex(@PathVariable("page") int page,
                            @RequestParam(value = "limit", required = false, defaultValue = "10") int limit,
                            HttpServletRequest request
    ) {
        page = page < 0 || page > WebConst.MAX_PAGE ? 1 : page;
        ContentQuery contentQuery = new ContentQuery();
        contentQuery.setType(Types.ARTICLE.getType());
        PageInfo<Content> articles = contentService.getArticlesByCond(contentQuery, page, limit);
        request.setAttribute("articles", articles);//文章列表
        request.setAttribute("type", "articles");
        request.setAttribute("active", "blog");
        this.blogBaseData(request);//获取公共分类标签等数据
        return "site/blog";
    }

    @ApiOperation("文章内容页")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "cid", value = "文章主键", paramType = "path", dataType = "int", required = true)
    })
    @GetMapping(value = "/blog/article/{cid}")
    public String post(@PathVariable("cid") Integer cid,
                       HttpServletRequest request
    ) {
        Content atricle = contentService.getAtricleById(cid);
        request.setAttribute("article", atricle);
        ContentQuery contentQuery = new ContentQuery();
        contentQuery.setType(Types.ARTICLE.getType());
//        this.blogBaseData(request, contentCond);//获取公共分类标签等数据
        //更新文章的点击量
//        this.updateArticleHit(atricle.getCid(),atricle.getHits());
        List<Comment> commentsPaginator = commentService.getCommentsByCId(cid);
        request.setAttribute("comments", commentsPaginator);
        request.setAttribute("active", "blog");
        return "site/blog-details";

    }

    @ApiOperation("归档页-按日期")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "date", value = "归档日期", paramType = "path", dataType = "String", required = true)
    })
    @GetMapping(value = "/blog/archives/{date}")
    public String archives(@PathVariable("date") String date,
                           HttpServletRequest request
    ) {
        ContentQuery contentQuery = new ContentQuery();
//        Date sd = DateKit.dateFormat(date, "yyyy年MM月");
//        int start = DateKit.getUnixTimeByDate(sd);
//        int end = DateKit.getUnixTimeByDate(DateKit.dateAdd(DateKit.INTERVAL_MONTH, sd, 1)) - 1;
//        contentQuery.setStartTime(start);
//        contentQuery.setEndTime(end);
        contentQuery.setType(Types.ARTICLE.getType());
        List<ArchiveDto> archives = siteService.getArchives(contentQuery);
        request.setAttribute("archives_list", archives);
//        this.blogBaseData(request, contentCond);//获取公共分类标签等数据
        return "blog/archives";
    }

    @ApiOperation("归档页")
    @GetMapping(value = {"/blog/archives", "/blog/archives/index"})
    public String archives(HttpServletRequest request) {
        ContentQuery contentQuery = new ContentQuery();
        contentQuery.setType(Types.ARTICLE.getType());
        List<ArchiveDto> archives = siteService.getArchives(contentQuery);
        request.setAttribute("archives_list", archives);
//        this.blogBaseData(request,contentCond);//获取公共分类标签等数据
        return "blog/archives";
    }

    @ApiOperation("分类")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "category", value = "分类名", paramType = "path", dataType = "String", required = true)
    })
    @GetMapping(value = "/blog/categories/{category}")
    public String categories(@PathVariable("category") String category,
                             HttpServletRequest request
    ) {
        return this.categories(category, 1, 10, request);
    }

    @ApiOperation("分类-分页")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "category", value = "分类名", paramType = "path", dataType = "String", required = true),
            @ApiImplicitParam(name = "page", value = "页数", paramType = "path", dataType = "int"),
            @ApiImplicitParam(name = "limit", value = "每页数量", paramType = "form", dataType = "int")
    })
    @GetMapping(value = "/blog/categories/{category}/page/{page}")
    public String categories(@PathVariable("category") String category,
                             @PathVariable("page") int page,
                             @RequestParam(name = "limit", required = false, defaultValue = "10") int limit,
                             HttpServletRequest request
    ) {
        ContentQuery contentCond = new ContentQuery();
        contentCond.setType(Types.ARTICLE.getType());
        contentCond.setCategory(category);
        PageInfo<Content> articles = contentService.getArticlesByCond(contentCond, page, limit);
//        this.blogBaseData(request,contentCond);//获取公共分类标签等数据
        request.setAttribute("articles_list", articles);
        request.setAttribute("type", "categories");
        request.setAttribute("param_name", category);
        return "blog/categories";

    }

    @ApiOperation("标签页")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "tag", value = "标签名", paramType = "path", dataType = "String", required = true)
    })
    @GetMapping(value = "/blog/tag/{tag}")
    public String tags(@PathVariable("tag") String tag,
                       HttpServletRequest request
    ) {
        return this.tags(tag, 1, 10, request);
    }

    @ApiOperation("标签页-分页")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "tag", value = "标签名", paramType = "path", dataType = "String", required = true),
            @ApiImplicitParam(name = "page", value = "页数", paramType = "path", dataType = "int", required = true),
            @ApiImplicitParam(name = "limit", value = "每页数量", paramType = "form", dataType = "int")
    })
    @GetMapping(value = "/blog/tag/{tag}/page/{page}")
    public String tags(@PathVariable("tag") String tag,
                       @PathVariable("page") int page,
                       @RequestParam(name = "limit", required = false, defaultValue = "10") int limit,
                       HttpServletRequest request
    ) {
        ContentQuery contentQuery = new ContentQuery();
        contentQuery.setTag(tag);
        contentQuery.setType(Types.ARTICLE.getType());
        PageInfo<Content> articles = contentService.getArticlesByCond(contentQuery, page, limit);
//        this.blogBaseData(request,contentCond);//获取公共分类标签等数据
        request.setAttribute("articles_list", articles);
        request.setAttribute("type", "tag");
        request.setAttribute("param_name", tag);
        return "blog/categories";
    }

    @ApiOperation("搜索文章")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "param", value = "搜索的文字", paramType = "form", dataType = "String", required = true)
    })
    @GetMapping("/blog/search")
    public String search(@RequestParam(name = "param") String param,
                         HttpServletRequest request
    ) {
        return this.search(param, 1, 10, request);
    }


    @ApiOperation("搜索文章-分页")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "param", value = "搜索的文字", paramType = "path", dataType = "String", required = true),
            @ApiImplicitParam(name = "page", value = "页数", paramType = "path", dataType = "int", required = true),
            @ApiImplicitParam(name = "limit", value = "每页数量", paramType = "form", dataType = "int")
    })
    @GetMapping(value = "/blog/search/{param}/page/{page}")
    public String search(@PathVariable("param") String param,
                         @PathVariable("page") int page,
                         @RequestParam(name = "limit", required = false, defaultValue = "10") int limit,
                         HttpServletRequest request
    ) {
        PageInfo<Content> pageInfo = contentService.searchArticle(param, page, limit);
        ContentQuery contentQuery = new ContentQuery();
        contentQuery.setType(Types.ARTICLE.getType());
//        this.blogBaseData(request,contentCond);//获取公共分类标签等数据
        request.setAttribute("articles", pageInfo);
        request.setAttribute("type", "search");
        request.setAttribute("param_name", param);
        return "blog/index";
    }


    @ApiOperation("作品主页")
    @GetMapping(value = {"", "/index"})
    public String index(@RequestParam(value = "limit", defaultValue = "12") int limit, HttpServletRequest request) {
        return this.index(1, limit, request);
    }

    @ApiOperation("作品主页-分页")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "page", value = "页数", paramType = "path", dataType = "int"),
            @ApiImplicitParam(name = "limit", value = "每页数量", paramType = "form", dataType = "int")
    })
    @GetMapping(value = "/photo/page/{page}")
    public String index(@PathVariable(name = "page") int page,
                        @RequestParam(name = "limit", required = false, defaultValue = "9999") int limit,
                        HttpServletRequest request
    ) {
        page = page < 0 || page > WebConst.MAX_PAGE ? 1 : page;
        ContentQuery contentCond = new ContentQuery();
        contentCond.setType(Types.PHOTO.getType());
        PageInfo<Content> articles = contentService.getArticlesByCond(contentCond, page, limit);
        request.setAttribute("archives", articles);
        request.setAttribute("active", "work");
        return "site/index";
    }


    @ApiOperation("作品内容")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "cid", value = "文章主键", paramType = "path", dataType = "int")
    })
    @GetMapping(value = "/photo/article/{cid}")
    public String article(@PathVariable("cid") Integer cid,
                          HttpServletRequest request
    ) {
        Content article = contentService.getAtricleById(cid);
        request.setAttribute("archive", article);
        request.setAttribute("active", "work");
        return "site/works-details";
    }

}
