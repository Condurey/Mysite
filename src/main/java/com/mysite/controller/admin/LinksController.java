package com.mysite.controller.admin;

import com.mysite.constant.ErrorConstant;
import com.mysite.constant.Types;
import com.mysite.exception.BusinessException;
import com.mysite.model.po.Meta;
import com.mysite.model.query.MetaQuery;
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

    @ApiOperation("新增友链")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "title", value = "标签", paramType = "form", dataType = "String", required = true),
            @ApiImplicitParam(name = "url", value = "链接", paramType = "form", dataType = "String", required = true),
            @ApiImplicitParam(name = "logo", value = "logo", paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "mid", value = "meta编号", paramType = "form", dataType = "int"),
            @ApiImplicitParam(name = "sort", value = "sort", paramType = "form", dataType = "int")
    })
    @PostMapping(value = "save")
    @ResponseBody
    public APIResponse addLink(@RequestParam(name = "title") String title,
                               @RequestParam(name = "url") String url,
                               @RequestParam(name = "logo", required = false) String logo,
                               @RequestParam(name = "mid", required = false) Integer mid,
                               @RequestParam(name = "sort", required = false, defaultValue = "0") int sort
    ) {
        try {
            Meta meta = new Meta();
            meta.setName(title);
            meta.setSlug(url);
            meta.setDescription(logo);
            meta.setSort(sort);
            meta.setType(Types.LINK.getType());
            if (null != mid) {
                meta.setMid(mid);
                metaService.updateMeta(meta);
            } else {
                metaService.addMeta(meta);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw BusinessException.withErrorCode(ErrorConstant.Meta.ADD_META_FAIL);
        }
        return APIResponse.success();
    }


    @ApiOperation("删除友链")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "mid", value = "meta主键", paramType = "form", dataType = "int", required = true)
    })
    @PostMapping(value = "delete")
    @ResponseBody
    public APIResponse delete(@RequestParam(name = "mid") int mid) {
        try {
            metaService.deleteMetaById(mid);
        } catch (Exception e) {
            e.printStackTrace();
            throw BusinessException.withErrorCode(ErrorConstant.Meta.ADD_META_FAIL);
        }
        return APIResponse.success();

    }


}
