package com.iris.blog.domain.vo;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * @author lstar
 * @date: 2024-04
 * @description: 系统操作日志
 */
@Data
@ApiModel(value="系统操作日志")
public class OperateLogVO implements Serializable {
	private static final long serialVersionUID = 1L;


	@ApiModelProperty(value = "主键ID")
	private Long id;

	@ApiModelProperty(value = "操作用户id")
	private Long userId;

	@ApiModelProperty(value = "操作用户名")
	private String userName;

	@ApiModelProperty(value = "请求的接口")
	private String requestUrl;

	@ApiModelProperty(value = "请求方式")
	private String requestType;

	@ApiModelProperty(value = "请求类名")
	private String requestClassName;

	@ApiModelProperty(value = "请求方法")
	private String requestMethod;

	@ApiModelProperty(value = "请求参数")
	private String requestParams;

	@ApiModelProperty(value = "请求耗时")
	private String requestTime;

	@ApiModelProperty(value = "请求ip")
	private String requestIp;

	@ApiModelProperty(value = "请求地址")
	private String requestAddress;

	@ApiModelProperty(value = "操作描述")
	private String operateDesc;

	@ApiModelProperty(value = "操作系统")
	private String operateOs;

	@ApiModelProperty(value = "异常信息json格式")
	private String errorInfo;

	@ApiModelProperty(value = "异常信息的message")
	private String errorMessage;

	@ApiModelProperty(value = "日志类型:0操作日志,1异常日志")
	private Boolean logType;

	@ApiModelProperty(value = "创建时间")
	private LocalDateTime createTime;

	@ApiModelProperty(value = "修改时间")
	private LocalDateTime updateTime;

}
