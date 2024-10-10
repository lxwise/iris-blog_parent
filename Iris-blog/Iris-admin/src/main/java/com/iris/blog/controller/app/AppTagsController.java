package com.iris.blog.controller.app;

import com.iris.blog.common.R;
import com.iris.blog.domain.vo.app.AppArticleTagVO;
import com.iris.blog.service.ArticleTagService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import java.util.List;

/**
 * @author lstar
 * @create 2024-05
 * @description: 博客标签-API
 */
@Validated
@RestController
@RequestMapping("/v1/tag")
@Api(tags = "标签分类接口")
public class AppTagsController {

    @Resource
    private ArticleTagService tagsService;

    @ApiOperation(value = "文章标签列表", httpMethod = "GET", response = R.class, notes = "文章标签列表")
    @GetMapping("/list")
    public R<List<AppArticleTagVO>> articleTagList(){
        return tagsService.articleTagList();
    }

}

