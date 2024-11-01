package com.iris.blog.domain.vo.app;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author lstar
 * @create 2024-08
 * @description: 文章详情VO
 */
@Data
@ApiModel(description = "文章详情VO")
public class AppArticleDetailVO {

    /**
     * 文章id
     */
    @ApiModelProperty(value = "文章id")
    private Long id;

    /**
     * 文章缩略图
     */
    @ApiModelProperty(value = "文章缩略图")
    private String articleCover;

    /**
     * 文章标题
     */
    @ApiModelProperty(value = "文章标题")
    private String articleTitle;

    /**
     * 文章内容
     */
    @ApiModelProperty(value = "文章内容")
    private String articleContent;

    /**
     * 文章类型 0：转载 1:原创
     */
    @ApiModelProperty(value = "文章类型 0：转载 1:原创")
    private Integer articleType;

    /**
     * 阅读方式 0无需验证 1点赞阅读
     */
    @ApiModelProperty(value = "阅读方式 0无需验证 1点赞阅读")
    private Integer readType;
    /**
     * 浏览量
     */
    @ApiModelProperty(value = "浏览量")
    private Integer viewCount;

    /**
     * 点赞量
     */
    @ApiModelProperty(value = "点赞量")
    private Integer likeCount;

    @ApiModelProperty(value = "分类id")
    private Long categoryId;

    @ApiModelProperty(value = "分类名称")
    private String categoryName;

    @ApiModelProperty(value = "文章标签,多个用逗号隔开,最多3个")
    @JsonIgnore
    private String tagJsonString;

    @ApiModelProperty(value = "文章标签列表")
    private List<AppTagOptionVO> tagVOList;

    /**
     * 上一篇文章
     */
    @ApiModelProperty(value = "上一篇文章")
    private AppArticlePaginationVO lastArticle;

    /**
     * 下一篇文章
     */
    @ApiModelProperty(value = "下一篇文章")
    private AppArticlePaginationVO nextArticle;

    /**
     * 发表时间
     */
    @ApiModelProperty(value = "发表时间")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

    public void setTagJsonString(String tagJsonString) {
        this.tagJsonString = tagJsonString;
        // 解析 JSON 字符串到 tagVOList
        if (tagJsonString != null && !tagJsonString.isEmpty()) {
            this.tagVOList = JSON.parseArray(tagJsonString, AppTagOptionVO.class);
        }
    }
    public List<AppTagOptionVO> getTagVOList() {
        return tagVOList;
    }
}