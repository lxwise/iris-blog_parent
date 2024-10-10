package com.iris.blog.domain.search;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author lstar
 * @date: 2024-04
 * @description: 文章
 */
@Data
@ApiModel(value="文章")
public class SearchArticleDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "分类id")
	private Long categoryId;

	@ApiModelProperty(value = "文章标签,多个用逗号隔开,最多3个")
	private String tags;

	@ApiModelProperty(value = "标题")
	private String title;

	@ApiModelProperty(value = "是否置顶 0否 1是")
	private Boolean isTop;

	@ApiModelProperty(value = "状态 0：草稿 1：发布 2:下架")
	private Integer status;

	@ApiModelProperty(value = "是否转载  0：转载 1:原创")
	private Boolean isForward;

	@ApiModelProperty("创建时间开始")
	private LocalDateTime createTimeStart;

	@ApiModelProperty("创建时间结束")
	private LocalDateTime createTimeEnd;
}
