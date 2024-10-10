package com.iris.blog.domain.vo.app;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author lstar
 * @date: 2024-04
 * @description: 标签列表
 */
@Data
@ApiModel(value="标签列表")
public class AppArticleTagVO {

	@ApiModelProperty(value = "主键id")
	private Long id;

	@ApiModelProperty(value = "标签名")
	private String tagName;

	@ApiModelProperty(value = "文章数量")
	private int articleCount;
}
