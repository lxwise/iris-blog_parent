package com.iris.blog.domain.dto.app;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author lstar
 * @date: 2024-04
 * @description: AppFeedBackDTO
 */
@Data
@ApiModel(value="AppFeedBackDTO")
public class AppFeedBackDTO {

	@ApiModelProperty(value = "标题")
	@NotNull(message = "标题不能为空")
	private String title;

	@ApiModelProperty(value = "详细内容")
	@NotNull(message = "详细内容不能为空")
	private String content;

	@ApiModelProperty(value = "图片地址")
	private String imgUrl;

	@ApiModelProperty(value = "反馈类型 0:需求 1：缺陷")
	@NotNull(message = "反馈类型不能为空")
	private Boolean backType;

}
