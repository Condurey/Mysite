package com.mysite.controller.admin;

import com.mysite.constant.Types;
import com.mysite.constant.WebConst;
import com.mysite.exception.BusinessException;
import com.mysite.model.dto.MetaDto;
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
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Api("分类和标签")
@Controller
@RequestMapping("admin/category")
public class CategoryController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CategoryController.class);

    @Autowired
    private MetaService metaService;


    @ApiOperation("进入分类和标签页")
    @GetMapping(value = "")
    public String index(HttpServletRequest request) {
        List<MetaDto> categories = metaService.getMetaList(Types.CATEGORY.getType(), null, WebConst.MAX_POSTS);
        List<MetaDto> tags = metaService.getMetaList(Types.TAG.getType(), null, WebConst.MAX_POSTS);
        request.setAttribute("categories", categories);
        request.setAttribute("tags", tags);
        return "admin/category";
    }

    @ApiOperation("保存分类")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "cname", value = "分类名", paramType = "form", dataType = "String", required = true),
            @ApiImplicitParam(name = "mid", value = "meta编号", paramType = "form", dataType = "int")
    })
    @PostMapping(value = "save")
    @ResponseBody
    public APIResponse addCategory(@RequestParam(name = "cname") String cname,
                                   @RequestParam(name = "mid", required = false) Integer mid
    ) {
        try {
            metaService.saveMeta(Types.CATEGORY.getType(), cname, mid);

        } catch (Exception e) {
            e.printStackTrace();
            String msg = "分类保存失败";
            if (e instanceof BusinessException) {
                BusinessException ex = (BusinessException) e;
                msg = ex.getErrorCode();
            }
            LOGGER.error(msg, e);

            return APIResponse.fail(msg);
        }
        return APIResponse.success();
    }

    @ApiOperation("删除分类")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "mid", value = "meta编号", paramType = "form", dataType = "int", required = true)
    })
    @PostMapping(value = "delete")
    @ResponseBody
    public APIResponse delete(@RequestParam(name = "mid") Integer mid) {
        try {
            metaService.deleteMetaById(mid);

        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
            return APIResponse.fail(e.getMessage());
        }
        return APIResponse.success();
    }

}
