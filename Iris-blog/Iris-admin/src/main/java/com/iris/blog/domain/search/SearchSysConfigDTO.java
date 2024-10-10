package com.iris.blog.domain.search;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author lstar
 * @date: 2024-04
 * @description: 参数管理
 */
@Data
@ApiModel(value="参数管理")
public class SearchSysConfigDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "参数编码")
	private String paramCode;

	@ApiModelProperty(value = "类型   0：系统参数   1：非系统参数")
	private Boolean paramType;

	@ApiModelProperty(value = "状态  0：不使用    1：使用")
	private Boolean status;


}
