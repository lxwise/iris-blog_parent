package com.iris.blog.domain.vo.app;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.reflect.TypeToken;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author lstar
 * @create 2024-08
 * @description: 文章首页VO
 */
@Data
@ApiModel(description = "文章首页VO")
public class AppArticleHomeVO {


    @ApiModelProperty(value = "文章id")
    private Integer id;

    @ApiModelProperty(value = "文章缩略图")
    private String articleCover;

    @ApiModelProperty(value = "文章标题")
    private String articleTitle;

    @ApiModelProperty(value = "文章内容")
    private String articleContent;

    @ApiModelProperty(value = "分类id")
    private Long categoryId;

    @ApiModelProperty(value = "分类名称")
    private String categoryName;

    @ApiModelProperty(value = "文章标签,多个用逗号隔开,最多3个")
    @JsonIgnore
    private String tagJsonString;

    @ApiModelProperty(value = "文章标签列表")
    private List<AppTagOptionVO> tagVOList;

    @ApiModelProperty(value = "是否置顶 0否 1是")
    private Boolean isTop;

    @ApiModelProperty(value = "发表时间")
    private LocalDateTime createTime;

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