package com.iris.blog.domain.vo.app;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author lstar
 * @date: 2024-04
 * @description: 分类列表
 */
@Data
@ApiModel(description = "分类列表VO")
public class AppArticleCategoryVO {

    /**
     * 分类id
     */
    @ApiModelProperty(value = "分类id")
    private Long id;

    /**
     * 分类名
     */
    @ApiModelProperty(value = "分类名")
    private String categoryName;

    /**
     * 文章数量
     */
    @ApiModelProperty(value = "文章数量")
    private Integer articleCount;

}