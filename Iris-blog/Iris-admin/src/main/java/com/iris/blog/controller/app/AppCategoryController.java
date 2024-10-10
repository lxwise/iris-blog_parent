package com.iris.blog.controller.app;

import com.iris.blog.common.PageBean;
import com.iris.blog.common.PageReq;
import com.iris.blog.common.R;
import com.iris.blog.domain.search.app.AppSearchAppArticleDTO;
import com.iris.blog.domain.vo.app.AppArticleCategoryVO;
import com.iris.blog.domain.vo.app.AppArticleHomeVO;
import com.iris.blog.service.ArticleCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * @author lstar
 * @create 2024-05
 * @description: 文章分类
 */
@Api(tags = "分类API-V1")
@RestController
@RequestMapping("/v1/category")
public class AppCategoryController {

    @Resource
    private ArticleCategoryService categoryService;

    @ApiOperation(value = "分类列表", httpMethod = "GET", response = R.class, notes = "分类列表")
    @GetMapping("/list")
    public R<List<AppArticleCategoryVO>> listCategory() {
        return this.categoryService.listCategory();
    }
    @ApiOperation(value = "分类文章列表", httpMethod = "POST", response = R.class, notes = "分类文章列表")
    @PostMapping("/article/list")
    public R<PageBean<AppArticleHomeVO>> articleCategoryList(@RequestBody @Valid  PageReq<AppSearchAppArticleDTO> req) {
        return this.categoryService.articleCategoryList(req);
    }

}
