package com.iris.blog.dao.entity;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.annotation.*;
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
@TableName("t_operate_log")
@ApiModel(value="系统操作日志")
public class OperateLogEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键ID
	 */
	@TableId(value = "id")
	@ApiModelProperty(value = "主键ID")
	private Long id;
	/**
	 * 操作用户id
	 */
	
	@TableField(value = "user_id")
	@ApiModelProperty(value = "操作用户id")
	private Long userId;
	/**
	 * 操作用户名
	 */
	
	@TableField(value = "user_name",condition = SqlCondition.LIKE)
	@ApiModelProperty(value = "操作用户名")
	private String userName;
	/**
	 * 请求的接口
	 */
	
	@TableField(value = "request_url",condition = SqlCondition.LIKE)
	@ApiModelProperty(value = "请求的接口")
	private String requestUrl;
	/**
	 * 请求方式
	 */
	
	@TableField(value = "request_type")
	@ApiModelProperty(value = "请求方式")
	private String requestType;
	/**
	 * 请求类名
	 */
	
	@TableField(value = "request_class_name")
	@ApiModelProperty(value = "请求类名")
	private String requestClassName;

	/**
	 * 请求方法
	 */

	@TableField(value = "request_method",condition = SqlCondition.LIKE)
	@ApiModelProperty(value = "请求方法")
	private String requestMethod;

	/**
	 * 请求参数
	 */
	
	@TableField(value = "request_params")
	@ApiModelProperty(value = "请求参数")
	private String requestParams;
	/**
	 * 请求耗时
	 */
	
	@TableField(value = "request_time")
	@ApiModelProperty(value = "请求耗时")
	private String requestTime;
	/**
	 * 请求ip
	 */
	
	@TableField(value = "request_ip")
	@ApiModelProperty(value = "请求ip")
	private String requestIp;
	/**
	 * 请求地址
	 */
	
	@TableField(value = "request_address")
	@ApiModelProperty(value = "请求地址")
	private String requestAddress;
	/**
	 * 操作描述
	 */
	
	@TableField(value = "operate_desc",condition = SqlCondition.LIKE)
	@ApiModelProperty(value = "操作描述")
	private String operateDesc;
	/**
	 * 操作系统
	 */
	
	@TableField(value = "operate_os")
	@ApiModelProperty(value = "操作系统")
	private String operateOs;
	/**
	 * 异常信息json格式
	 */
	
	@TableField(value = "error_info")
	@ApiModelProperty(value = "异常信息json格式")
	private String errorInfo;
	/**
	 * 日志类型:0操作日志,1异常日志
	 */

	@TableField(value = "log_type")
	@ApiModelProperty(value = "日志类型:0操作日志,1异常日志")
	private Boolean logType;
	/**
	 * 异常信息的message
	 */
	
	@TableField(value = "error_message")
	@ApiModelProperty(value = "异常信息的message")
	private String errorMessage;
	/**
	 * 创建时间
	 */
	
	@TableField(value = "create_time", fill = FieldFill.INSERT)
	@ApiModelProperty(value = "创建时间")
	private LocalDateTime createTime;
	/**
	 * 修改时间
	 */
	
	@TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
	@ApiModelProperty(value = "修改时间")
	private LocalDateTime updateTime;
}
