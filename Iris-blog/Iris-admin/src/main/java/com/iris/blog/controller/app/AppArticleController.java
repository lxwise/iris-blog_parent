package com.iris.blog.controller.app;

import cn.dev33.satoken.annotation.SaCheckLogin;
import com.iris.blog.common.PageBean;
import com.iris.blog.common.PageReq;
import com.iris.blog.common.R;
import com.iris.blog.common.annotation.RLimit;
import com.iris.blog.common.enums.LikeTypeEnum;
import com.iris.blog.domain.search.app.AppSearchAppArticleDTO;
import com.iris.blog.domain.vo.app.*;
import com.iris.blog.service.ArticleService;
import com.iris.blog.strategy.context.LikeStrategyContext;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * @author lstar
 * @create 2024-05
 * @description: 博客文章
 */
@RestController
@RequestMapping("/v1/article")
@Api(tags = "文章API-V1")
public class AppArticleController {

    @Resource
    private ArticleService articleService;

    @Resource
    private LikeStrategyContext likeStrategyContext;

    @ApiOperation(value = "推荐文章列表")
    @GetMapping("/recommend")
    public R<List<AppArticleRecommendVO>> articleRecommendList() {
        return articleService.articleRecommendList();
    }

    @ApiOperation(value = "文章首页列表", httpMethod = "POST", response = R.class, notes = "文章首页列表")
    @PostMapping("/list")
    public R<PageBean<AppArticleHomeVO>> articleHomeList(@RequestBody @Valid PageReq<AppSearchAppArticleDTO>  req){
        return articleService.articleHomeList(req);
    }

    @ApiOperation(value = "文章归档列表", httpMethod = "POST", response = R.class, notes = "文章归档列表")
    @PostMapping("/archives/list")
    public R<PageBean<AppArticleArchiveVO>> articleArchivesList(@RequestBody @Valid PageReq<?>  req){
        return articleService.articleArchivesList(req);
    }

    @ApiOperation(value = "文章搜索", httpMethod = "POST", response = R.class, notes = "文章搜索")
    @PostMapping("/article/search")
    public R<List<AppArticleSearchVO>> articleSearchList(@RequestBody @Valid AppSearchAppArticleDTO dto){
        return articleService.articleSearchList(dto);
    }

    @ApiOperation(value = "查看文章", httpMethod = "GET", response = R.class, notes = "查看文章")
    @GetMapping("/article/{articleId}")
    public R<AppArticleDetailVO> getArticleHomeById(@PathVariable("articleId") Integer articleId) {
        return articleService.getArticleHomeById(articleId);
    }

    @SaCheckLogin
    @ApiOperation(value = "点赞文章", httpMethod = "POST", response = R.class, notes = "点赞文章")
    @RLimit(count = 3, time = 60)
    @PostMapping("/article/{articleId}/like")
    public R likeArticle(@PathVariable("articleId") Integer articleId) {
        likeStrategyContext.executeLikeStrategy(LikeTypeEnum.ARTICLE, articleId);
        return R.ok();
    }

}
