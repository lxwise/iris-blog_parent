package com.iris.blog.domain.search;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author lstar
 * @date: 2024-04
 * @description: 用户反馈
 */
@Data
@ApiModel(value="用户反馈")
public class SearchFeedBackDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "反馈类型 0:需求 1：缺陷")
	private Boolean backType;

	@ApiModelProperty(value = "状态 0未解决 1解决")
	private Boolean status;

}
