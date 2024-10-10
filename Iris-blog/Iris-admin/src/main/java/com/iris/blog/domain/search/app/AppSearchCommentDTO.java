package com.iris.blog.domain.search.app;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * @author lstar
 * @date: 2024-04
 * @description: 评论查询搜索
 */
@Data
@ApiModel(description = "评论查询搜索")
public class AppSearchCommentDTO {

    /**
     * 评论id
     */
    @ApiModelProperty(value = "评论id")
    private Long commentId;

    /**
     * 类型id
     */
    @ApiModelProperty(value = "类型id")
    private Integer typeId;

    /**
     * 评论主题类型
     */
    @ApiModelProperty(value = "评论主题类型")
    private Integer commentType;
}