package com.iris.blog.controller.system;

import java.util.List;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import com.iris.blog.common.annotation.OperateLog;
import com.iris.blog.domain.search.SearchNameDTO;
import com.iris.blog.domain.vo.ArticleCategoryVO;
import com.iris.blog.domain.vo.ArticleTagVO;
import org.springframework.beans.factory.annotation.Autowired;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.iris.blog.domain.dto.ArticleTagDTO;
import com.iris.blog.service.ArticleTagService;
import com.iris.blog.common.R;
import com.iris.blog.common.PageReq;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author lstar
 * @date: 2024-04
 * @description: 文章标签表
 */

@Api(tags = "文章标签管理")
@Validated
@RestController
@RequestMapping("/system/article/tag")
public class ArticleTagController {
    @Autowired
    private ArticleTagService articleTagService;

    @ApiOperation(value = "文章标签列表", httpMethod = "POST", response = R.class, notes = "文章标签列表")
    @SaCheckPermission("system:articletag:query")
    @PostMapping("/list")
    public R selectArticleTagList(@RequestBody @Valid  PageReq<SearchNameDTO> req){

        return articleTagService.selectArticleTagList(req);
    }

    @ApiOperation(value = "文章标签信息", httpMethod = "GET", response = R.class, notes = "文章标签信息")
    @SaCheckPermission("system:articletag:query")
    @GetMapping("/info")
    public R selectArticleTagById(@NotNull Long id){

        return articleTagService.selectArticleTagById(id);
    }

    @ApiOperation(value = "保存文章标签", httpMethod = "POST", response = R.class, notes = "保存文章标签")
    @SaCheckPermission("system:articletag:save")
    @PostMapping("/save")
    public R saveArticleTag(@RequestBody @Valid  ArticleTagDTO articleTag){

        return articleTagService.saveArticleTag(articleTag);
    }

    @ApiOperation(value = "修改文章标签", httpMethod = "POST", response = R.class, notes = "修改文章标签")
    @SaCheckPermission("system:articletag:update")
    @PostMapping("/update")
    public R updateArticleTag(@RequestBody @Valid  ArticleTagDTO articleTag){

        return articleTagService.updateArticleTag(articleTag);
    }


    @ApiOperation(value = "删除文章标签", httpMethod = "POST", response = R.class, notes = "删除文章标签")
    @SaCheckPermission("system:articletag:delete")
    @OperateLog(value = "删除文章标签")
    @PostMapping("/delete")
    public R removeArticleTagByIds(@RequestBody @NotEmpty List<Long> ids){

        return articleTagService.removeArticleTagByIds(ids);
    }

    @GetMapping("/select")
    @SaCheckLogin
    @ApiOperation(value = "文章标签下拉", httpMethod = "GET", response = R.class, notes = "文章标签下拉")
    public R<List<ArticleTagVO>> selectList(String name){

        return R.ok(articleTagService.selectList(name));
    }

}
