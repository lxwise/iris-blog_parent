package com.iris.blog.domain.search;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author lstar
 * @date: 2024-04
 * @description:
 */
@Data
public class SearchNameDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "名称")
	private String name;

	@ApiModelProperty(value = "状态:0未审核,1审核通过,2驳回")
	private Integer status;

	@ApiModelProperty("创建时间开始")
	private LocalDateTime createTimeStart;

	@ApiModelProperty("创建时间结束")
	private LocalDateTime createTimeEnd;

}
