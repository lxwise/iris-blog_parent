package com.iris.blog.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Data;

/**
 * @author lstar
 * @date: 2024-04
 * @description: 系统操作日志
 */
@Data
@ApiModel(value="系统操作日志")
public class OperateLogDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "操作用户名")
	private String userName;

	@ApiModelProperty(value = "请求的接口")
	private String requestUrl;

	@ApiModelProperty(value = "请求方式")
	private String requestType;

	@ApiModelProperty(value = "请求方法")
	private String requestMethod;

	@ApiModelProperty(value = "操作描述")
	private String operateDesc;

	@ApiModelProperty(value = "日志类型:0操作日志,1异常日志")
	private Boolean logType;

	@ApiModelProperty("创建时间开始")
	private LocalDateTime createTimeStart;

	@ApiModelProperty("创建时间结束")
	private LocalDateTime createTimeEnd;

}
