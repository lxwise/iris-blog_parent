package com.iris.blog.controller.system;

import java.util.List;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaCheckRole;
import com.iris.blog.common.annotation.OperateLog;
import com.iris.blog.domain.search.SearchNameDTO;
import com.iris.blog.domain.vo.ArticleCategoryVO;
import com.iris.blog.domain.vo.DictDataVO;
import org.springframework.beans.factory.annotation.Autowired;
import javax.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.iris.blog.domain.dto.ArticleCategoryDTO;
import com.iris.blog.service.ArticleCategoryService;
import com.iris.blog.common.R;
import com.iris.blog.common.PageReq;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author lstar
 * @date: 2024-04
 * @description: 文章分类表
 */

@Api(tags = "文章分类管理")
@Validated
@RestController
@RequestMapping("/system/article/category")
public class ArticleCategoryController {
    @Autowired
    private ArticleCategoryService articleCategoryService;

    @ApiOperation(value = "文章分类列表", httpMethod = "POST", response = R.class, notes = "文章分类列表")
    @SaCheckPermission("system:articlecategory:query")
    @PostMapping("/list")
    public R selectArticleCategoryList(@RequestBody @Valid  PageReq<SearchNameDTO> req){

        return articleCategoryService.selectArticleCategoryList(req);
    }

    @ApiOperation(value = "文章分类信息", httpMethod = "GET", response = R.class, notes = "文章分类信息")
    @SaCheckPermission("system:articlecategory:query")
    @GetMapping("/info")
    public R selectArticleCategoryById( Long id){

        return articleCategoryService.selectArticleCategoryById(id);
    }

    @ApiOperation(value = "保存文章分类", httpMethod = "POST", response = R.class, notes = "保存文章分类")
    @SaCheckPermission("system:articlecategory:save")
    @PostMapping("/save")
    public R saveArticleCategory(@RequestBody @Valid  ArticleCategoryDTO articleCategory){

        return articleCategoryService.saveArticleCategory(articleCategory);
    }

    @ApiOperation(value = "修改文章分类", httpMethod = "POST", response = R.class, notes = "修改文章分类")
    @SaCheckPermission("system:articlecategory:update")
    @SaCheckRole("super_admin")
    @PostMapping("/update")
    public R updateArticleCategory(@RequestBody @Valid  ArticleCategoryDTO articleCategory){

        return articleCategoryService.updateArticleCategory(articleCategory);
    }


    @ApiOperation(value = "删除文章分类", httpMethod = "POST", response = R.class, notes = "删除文章分类")
    @SaCheckPermission("system:articlecategory:delete")
    @OperateLog(value = "删除文章分类")
    @PostMapping("/delete")
    public R removeArticleCategoryByIds(@RequestBody List<Long> ids){

        return articleCategoryService.removeArticleCategoryByIds(ids);
    }


    @GetMapping("/select")
    @SaCheckLogin
    @ApiOperation(value = "文章分类下拉", httpMethod = "GET", response = R.class, notes = "文章分类下拉")
    public R<List<ArticleCategoryVO>> selectList(String name){

        return articleCategoryService.selectList(name);
    }

}
