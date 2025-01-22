package com.iris.blog.controller.system;

import java.util.List;
import cn.dev33.satoken.annotation.SaCheckPermission;
import com.iris.blog.common.annotation.OperateLog;
import com.iris.blog.domain.search.SearchArticleDTO;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.iris.blog.domain.dto.ArticleDTO;
import com.iris.blog.service.ArticleService;
import com.iris.blog.common.R;
import com.iris.blog.common.PageReq;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author lstar
 * @date: 2024-04
 * @description: 文章管理
 */

@Api(tags = "文章管理")
@Validated
@RestController
@RequestMapping("/system/article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @ApiOperation(value = "文章列表", httpMethod = "POST", response = R.class, notes = "文章列表")
    @SaCheckPermission("system:article:query")
    @PostMapping("/list")
    public R selectArticleList(@RequestBody @Valid  PageReq<SearchArticleDTO> req){

        return articleService.selectArticleList(req);
    }

    @ApiOperation(value = "文章信息", httpMethod = "GET", response = R.class, notes = "文章信息")
    @SaCheckPermission("system:article:query")
    @GetMapping("/info")
    public R selectArticleById(@NotNull Long id){

        return articleService.selectArticleById(id);
    }

    @ApiOperation(value = "保存文章", httpMethod = "POST", response = R.class, notes = "保存文章")
    @SaCheckPermission("system:article:save")
    @OperateLog(value = "保存文章")
    @PostMapping("/save")
    public R saveArticle(@RequestBody @Valid  ArticleDTO article){

        return articleService.saveArticle(article);
    }

    @ApiOperation(value = "修改文章", httpMethod = "POST", response = R.class, notes = "修改文章")
    @SaCheckPermission("system:article:update")
    @OperateLog(value = "修改文章")
    @PostMapping("/update")
    public R updateArticle(@RequestBody @Valid  ArticleDTO article){

        return articleService.updateArticle(article);
    }


    @ApiOperation(value = "删除文章", httpMethod = "POST", response = R.class, notes = "删除文章")
    @SaCheckPermission("system:article:delete")
    @OperateLog(value = "删除文章")
    @PostMapping("/delete")
    public R removeArticleByIds(@RequestBody @NotEmpty  List<Long> ids){

        return articleService.removeArticleByIds(ids);
    }

    @GetMapping(value = "/top")
    @ApiOperation(value = "置顶文章", httpMethod = "GET", response = R.class, notes = "置顶文章")
    @SaCheckPermission("system:article:update")
    public R topArticle(@NotNull Long id) {
        return articleService.topArticle(id);
    }

    @GetMapping(value = "/down")
    @ApiOperation(value = "发布或下架文章", httpMethod = "GET", response = R.class, notes = "发布或下架文章")
    @OperateLog(value = "发布或下架文章")
    @SaCheckPermission("system:article:update")
    public R psArticle(@NotNull Long id, @NotNull Integer status) {
        return articleService.psArticle(id,status);
    }


    @PostMapping("/import")
    @ApiOperation(value = "文章导入", httpMethod = "POST", response = R.class, notes = "文章导入")
    @SaCheckPermission("system:article:import")
    public R importArticle(@RequestParam("file") MultipartFile file) {

        return R.ok(articleService.importArticle(file));
    }

    @PostMapping("/export")
    @ApiOperation(value = "文章导出", httpMethod = "POST", response = void.class, notes = "文章导出")
    @SaCheckPermission("system:article:export")
    public void exportArticle(@RequestBody @NotEmpty List<Long> ids, HttpServletResponse response) {
        articleService.exportArticle(ids, response);
    }

}
