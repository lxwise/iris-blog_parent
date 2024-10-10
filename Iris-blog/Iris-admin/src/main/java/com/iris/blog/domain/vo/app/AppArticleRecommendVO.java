package com.iris.blog.domain.vo.app;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;


/**
 * @author lstar
 * @create 2024-08
 * @description: 推荐文章
 */
@Data
@ApiModel(description = "推荐文章--ArticleRecommendVO")
public class AppArticleRecommendVO {

    /**
     * 文章id
     */
    @ApiModelProperty(value = "文章id")
    private Integer id;

    /**
     * 文章标题
     */
    @ApiModelProperty(value = "文章标题")
    private String articleTitle;

    /**
     * 文章缩略图
     */
    @ApiModelProperty(value = "文章缩略图")
    private String articleCover;

    /**
     * 发布时间
     */
    @ApiModelProperty(value = "发布时间")
    private LocalDateTime createTime;
}