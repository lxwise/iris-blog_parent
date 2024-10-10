package com.iris.blog.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * @author lstar
 * @date: 2024-04
 * @description: 参数管理
 */
@Data
@ApiModel(value="参数管理")
public class SysConfigDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "id")
	private Long id;

	@ApiModelProperty(value = "参数编码")
	@NotNull(message = "{paramCode.NotNull}")
	private String paramCode;

	@ApiModelProperty(value = "参数值")
	@NotNull(message = "{paramValue.NotNull}")
	private String paramValue;

	@ApiModelProperty(value = "类型   0：系统参数   1：非系统参数")
	@NotNull(message = "{paramType.NotNull}")
	private Boolean paramType;

	@ApiModelProperty(value = "备注")
	private String remark;

	@ApiModelProperty(value = "状态  0：不使用    1：使用")
	@NotNull(message = "{paramType.NotNull}")
	private Boolean status;

}
