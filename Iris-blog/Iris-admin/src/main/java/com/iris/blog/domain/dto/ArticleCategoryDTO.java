package com.iris.blog.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

/**
 * @author lstar
 * @date: 2024-04
 * @description: 文章分类表
 */
@Data
@ApiModel(value="ArticleCategoryDTO")
public class ArticleCategoryDTO implements Serializable {
	private static final long serialVersionUID = 1L;


	@ApiModelProperty(value = "主键id")
	private Long id;

	@ApiModelProperty(value = "分类名称")
	@NotNull(message = "分类名称不能为空")
	@Size(max = 20, message = "分类名称不能超过20个字符")
	private String name;

	@ApiModelProperty(value = "排序")
	@NotNull(message = "排序不能为空")
	@Min(value = 0, message = "排序不能小于0")
	private Integer sort;

	@ApiModelProperty(value = "图标")
	private String icon;

}
