package com.mysite.controller.admin;

import com.github.pagehelper.PageInfo;
import com.mysite.constant.ErrorConstant;
import com.mysite.controller.BaseController;
import com.mysite.exception.BusinessException;
import com.mysite.model.po.Comment;
import com.mysite.model.query.CommentQuery;
import com.mysite.service.CommentService;
import com.mysite.utils.APIResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


@Api("评论相关接口")
@Controller
@RequestMapping("/admin/comments")
public class CommentController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommentController.class);

    @Autowired
    private CommentService commentService;


    @ApiOperation("进入评论列表页")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "page", value = "页数", paramType = "form", dataType = "int"),
            @ApiImplicitParam(name = "limit", value = "每页数量", paramType = "form", dataType = "int")
    })
    @GetMapping(value = "")
    public String index(@RequestParam(name = "page", required = false, defaultValue = "1") int page,
                        @RequestParam(name = "limit", required = false, defaultValue = "15") int limit,
                        HttpServletRequest request
    ) {
        PageInfo<Comment> comments = commentService.getCommentsByCond(new CommentQuery(), page, limit);
        request.setAttribute("comments", comments);
        return "admin/comment_list";
    }

    @ApiOperation("删除一条评论")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "coid", value = "评论编号", paramType = "form", dataType = "int", required = true)
    })
    @PostMapping(value = "/delete")
    @ResponseBody
    public APIResponse deleteComment(@RequestParam(name = "coid") Integer coid) {

        try {
            Comment comment = commentService.getCommentById(coid);
            if (null == comment)
                throw BusinessException.withErrorCode(ErrorConstant.Comment.COMMENT_NOT_EXIST);

            commentService.deleteComment(coid);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
            return APIResponse.fail(e.getMessage());
        }
        return APIResponse.success();
    }

    @ApiOperation("更改评论状态")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "coid", value = "评论主键", paramType = "form", dataType = "int", required = true),
            @ApiImplicitParam(name = "status", value = "状态", paramType = "form", dataType = "String", required = true)
    })
    @PostMapping(value = "/status")
    @ResponseBody
    public APIResponse changeStatus(@RequestParam(name = "coid") Integer coid,
                                    @RequestParam(name = "status") String status
    ) {
        try {
            Comment comment = commentService.getCommentById(coid);
            if (null != comment) {
                commentService.updateCommentStatus(coid, status);
            } else {
                return APIResponse.fail("删除失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
            return APIResponse.fail(e.getMessage());
        }
        return APIResponse.success();
    }

}
