package com.mysite.controller.admin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/article")
public class ArticleController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ArticleController.class);

//    /**
//     * 文章发表
//     * @param request
//     * @return
//     */
//    @GetMapping(value = "/publish")
//    public String newArticle(HttpServletRequest request) {
//        List<Meta> categories = metasService.getMetas(Types.CATEGORY.getType());
//        request.setAttribute("categories", categories);
//        return "admin/article_edit";
//    }
}
