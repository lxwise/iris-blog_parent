package com.iris.blog.controller.system;


import org.springframework.beans.factory.annotation.Autowired;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.iris.blog.service.ArticleCollectService;
import com.iris.blog.common.R;
import com.iris.blog.common.PageReq;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author lstar
 * @date: 2024-04
 * @description: 文章收藏API
 */
@Api(tags = "文章收藏API-V1")
@Validated
@RestController
@RequestMapping("/v1/collect")
public class ArticleCollectController {
    @Autowired
    private ArticleCollectService articleCollectService;

    @ApiOperation(value = "我的收藏列表", httpMethod = "POST", response = R.class, notes = "我的收藏列表")
    @PostMapping("/list")
    public R selectArticleCollectList(@RequestBody @Valid  PageReq req){

        return articleCollectService.selectArticleCollectList(req);
    }

    @ApiOperation(value = "收藏文章", httpMethod = "GET", response = R.class, notes = "收藏文章")
    @GetMapping("/collect")
    public R collect(@NotNull Long id){

        return articleCollectService.collect(id);
    }
    @ApiOperation(value = "取消收藏", httpMethod = "GET", response = R.class, notes = "取消收藏")
    @GetMapping("/cancel/collect")
    public R cancelCollect(@NotNull Long id){

        return articleCollectService.cancelCollect(id);
    }


}
