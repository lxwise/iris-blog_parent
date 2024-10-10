package com.iris.blog.domain.search.app;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author lstar
 * @date: 2024-04
 * @description: 文章搜索
 */
@Data
@ApiModel(value="文章搜索")
public class AppSearchAppArticleDTO {

	@ApiModelProperty(value = "分类id")
	private Long categoryId;

	@ApiModelProperty(value = "文章标签")
	private String tags;

	@ApiModelProperty(value = "关键词搜索")
	private String keyword;
}
