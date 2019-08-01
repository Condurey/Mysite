package com.mysite.controller;


import com.github.pagehelper.PageInfo;
import com.mysite.constant.Types;
import com.mysite.constant.WebConst;
import com.mysite.exception.BusinessException;
import com.mysite.interceptor.AuthService;
import com.mysite.model.dto.ArchiveDto;
import com.mysite.model.dto.MetaDto;
import com.mysite.model.po.Comment;
import com.mysite.model.po.Content;
import com.mysite.model.po.Meta;
import com.mysite.model.query.ContentQuery;
import com.mysite.model.query.MetaQuery;
import com.mysite.service.CommentService;
import com.mysite.service.ContentService;
import com.mysite.service.MetaService;
import com.mysite.service.SiteService;
import com.mysite.utils.APIResponse;
import com.mysite.utils.BlogUtils;
import com.mysite.utils.PatternUtils;
import com.vdurmont.emoji.EmojiParser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
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

    @Autowired
    private MetaService metaService;

    @Autowired
    private AuthService authService;

    @ApiIgnore
    @GetMapping(value = {"/about"})
    public String getAbout(HttpServletRequest request) {
        return "site/about";
    }

    @ApiOperation("blog首页")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "limit", value = "每页数量", paramType = "form", dataType = "int")
    })
    @GetMapping(value = {"", "/index"})
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
    @GetMapping(value = "/page/{page}")
    public String blogIndex(@PathVariable("page") int page,
                            @RequestParam(value = "limit", required = false, defaultValue = "10") int limit,
                            HttpServletRequest request
    ) {
        page = page < 0 || page > WebConst.MAX_PAGE ? 1 : page;
        ContentQuery contentQuery = new ContentQuery();
        contentQuery.setType(Types.ARTICLE.getType());
        PageInfo<Content> articles = contentService.getArticlesByCond(contentQuery, page, limit);
        request.setAttribute("articles", articles);//文章列表
        if (page > 1) {
            this.title(request, "第" + page + "页");
        }
        return "site/index";
    }

    @ApiOperation("文章内容页")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "cid", value = "文章主键", paramType = "path", dataType = "int", required = true)
    })
    @GetMapping(value = "/article/{cid}")
    public String post(@PathVariable("cid") Integer cid,
                       HttpServletRequest request
    ) {
        Content atricle = contentService.getAtricleById(cid);
        request.setAttribute("article", atricle);
        //更新文章的点击量
//        this.updateArticleHit(atricle.getCid(),atricle.getHits());
        List<Comment> comments = commentService.getCommentsByCId(cid);
        if (null != comments && comments.size() > 0) {
            request.setAttribute("comments", comments);
        }
        request.setAttribute("is_post", true);
        return "site/blog-details";

    }

    /**
     * 评论操作
     */
    @PostMapping(value = "/comment")
    @ResponseBody
    @Transactional(rollbackFor = BusinessException.class)
    public APIResponse comment(@RequestParam Integer cid, @RequestParam Integer coid,
                               @RequestParam String author, @RequestParam String mail,
                               @RequestParam String url, @RequestParam String text,
                               @RequestParam String _csrf_token,
                               HttpServletRequest request,
                               HttpServletResponse response) {

        String ref = request.getHeader("Referer");
        if (StringUtils.isBlank(ref) || StringUtils.isBlank(_csrf_token)) {
            return APIResponse.fail("非法请求");
        }

//        String token = cache.hget(Types.CSRF_TOKEN.getType(), _csrf_token);
//        if (StringUtils.isBlank(token)) {
//            return APIResponse.fail(ErrorCode.BAD_REQUEST);
//        }

        if (null == cid || StringUtils.isBlank(text)) {
            return APIResponse.fail("请输入完整后评论");
        }

        if (StringUtils.isNotBlank(author) && author.length() > 50) {
            return APIResponse.fail("姓名过长");
        }

        if (StringUtils.isNotBlank(mail) && !PatternUtils.isEmail(mail)) {
            return APIResponse.fail("请输入正确的邮箱格式");
        }

        if (StringUtils.isNotBlank(url) && !PatternUtils.isURL(url)) {
            return APIResponse.fail("请输入正确的URL格式");
        }

        if (text.length() > 200) {
            return APIResponse.fail("请输入200个字符以内的评论");
        }

        String val = authService.getIpAddrByRequest(request) + ":" + cid;
//        Integer count = cache.hget(Types.COMMENTS_FREQUENCY.getType(), val);
//        if (null != count && count > 0) {
//            return APIResponse.fail("您发表评论太快了，请过会再试");
//        }

        author = BlogUtils.cleanXSS(author);
        text = BlogUtils.cleanXSS(text);

        author = EmojiParser.parseToAliases(author);
        text = EmojiParser.parseToAliases(text);

        Comment comment = new Comment();
        comment.setAuthor(author);
        comment.setCid(cid);
        comment.setIp(request.getRemoteAddr());
        comment.setUrl(url);
        comment.setContent(text);
        comment.setMail(mail);
        comment.setParent(coid);
        try {
            commentService.addComment(comment);
            authService.setCookie("tale_remember_author", URLEncoder.encode(author, "UTF-8"), 7 * 24 * 60 * 60, response);
            authService.setCookie("tale_remember_mail", URLEncoder.encode(mail, "UTF-8"), 7 * 24 * 60 * 60, response);
            if (StringUtils.isNotBlank(url)) {
                authService.setCookie("tale_remember_url", URLEncoder.encode(url, "UTF-8"), 7 * 24 * 60 * 60, response);
            }
            // 设置对每个文章1分钟可以评论一次
//            cache.hset(Types.COMMENTS_FREQUENCY.getType(), val, 1, 60);
            return APIResponse.success();
        } catch (Exception e) {
            String msg = "评论发布失败";
            if (e instanceof BusinessException) {
                msg = e.getMessage();
            } else {
//                LOGGER.error(msg, e);
            }
            return APIResponse.fail(msg);
        }
    }

    @ApiOperation("归档页")
    @GetMapping(value = {"/archives"})
    public String archives(HttpServletRequest request) {
        ContentQuery contentQuery = new ContentQuery();
        contentQuery.setType(Types.ARTICLE.getType());
        List<ArchiveDto> archives = siteService.getArchives(contentQuery);
        request.setAttribute("archives", archives);
        return "site/archives";
    }

    /**
     * 友链页
     *
     * @return
     */
    @GetMapping(value = "/links")
    public String links(HttpServletRequest request) {
        MetaQuery metaQuery = new MetaQuery();
        metaQuery.setType(Types.LINK.getType());
        List<Meta> links = metaService.getMetas(metaQuery);
        request.setAttribute("links", links);
        return "site/links";
    }

    @ApiOperation("分类")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "category", value = "分类名", paramType = "path", dataType = "String", required = true)
    })
    @GetMapping(value = "/category/{category}")
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
    @GetMapping(value = "/category/{category}/page/{page}")
    public String categories(@PathVariable("category") String category,
                             @PathVariable("page") int page,
                             @RequestParam(name = "limit", required = false, defaultValue = "10") int limit,
                             HttpServletRequest request
    ) {
        MetaQuery metaQuery = new MetaQuery();
        metaQuery.setType(Types.CATEGORY.getType());
        metaQuery.setName(category);
        MetaDto metaDto = metaService.getMetaByQuery(metaQuery);

        PageInfo<Content> articles = contentService.getArticlesByCatalog(metaDto.getMid(), page, limit);
        request.setAttribute("articles", articles);
        request.setAttribute("meta", metaDto);
        request.setAttribute("type", "分类");
        request.setAttribute("category", category);
        return "site/category";

    }

    @ApiOperation("标签页")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "tag", value = "标签名", paramType = "path", dataType = "String", required = true)
    })
    @GetMapping(value = "/tag/{tag}")
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
    @GetMapping(value = "/tag/{tag}/page/{page}")
    public String tags(@PathVariable("tag") String tag,
                       @PathVariable("page") int page,
                       @RequestParam(name = "limit", required = false, defaultValue = "10") int limit,
                       HttpServletRequest request
    ) {
        MetaQuery metaQuery = new MetaQuery();
        metaQuery.setType(Types.TAG.getType());
        metaQuery.setName(tag);
        MetaDto metaDto = metaService.getMetaByQuery(metaQuery);

        PageInfo<Content> articles = contentService.getArticlesByCatalog(metaDto.getMid(), page, limit);
        request.setAttribute("articles", articles);
        request.setAttribute("meta", metaDto);
        request.setAttribute("type", "标签");
        request.setAttribute("category", tag);
        return "site/category";
    }

    @ApiOperation("搜索文章")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "param", value = "搜索的文字", paramType = "form", dataType = "String", required = true)
    })
    @GetMapping("/search")
    public String search(@RequestParam(name = "param") String param,
                         HttpServletRequest request
    ) {
        return this.search(param, 1, 10, request);
    }


    @ApiOperation("搜索文章-分页")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "keyword", value = "搜索的文字", paramType = "path", dataType = "String", required = true),
            @ApiImplicitParam(name = "page", value = "页数", paramType = "path", dataType = "int", required = true),
            @ApiImplicitParam(name = "limit", value = "每页数量", paramType = "form", dataType = "int")
    })
    @GetMapping(value = "/search/{keyword}/page/{page}")
    public String search(@PathVariable("keyword") String keyword,
                         @PathVariable("page") int page,
                         @RequestParam(name = "limit", required = false, defaultValue = "10") int limit,
                         HttpServletRequest request
    ) {
        PageInfo<Content> articles = contentService.searchArticle(keyword, page, limit);
        request.setAttribute("articles", articles);
        request.setAttribute("type", "搜索");
        request.setAttribute("keyword", keyword);
        return "site/category";
    }


    /**
     * 抽取公共方法
     *
     * @param Content
     * @param request
     */
    private void completeArticle(Content Content, HttpServletRequest request) {
        if (Content.getAllowComment()) {
//            String cp = request.getParameter("cp");
//            if (StringUtils.isBlank(cp)) {
//                cp = "1";
//            }
//            request.setAttribute("cp", cp);
            List<Comment> comments = commentService.getCommentsByCId(Content.getCid());
            request.setAttribute("comments", comments);
        }
    }

}
