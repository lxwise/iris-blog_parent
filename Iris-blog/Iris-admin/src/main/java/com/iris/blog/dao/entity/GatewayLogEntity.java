package com.iris.blog.dao.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * @author lstar
 * @date: 2024-06
 * @description: 网关日志
 */
@Data
@TableName("t_gateway_log")
@ApiModel(value="网关日志")
public class GatewayLogEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键id
	 */
	@TableId(value = "id")
	@ApiModelProperty(value = "主键id")
	private Long id;
	/**
	 * IP地址
	 */
	
	@TableField(value = "ip")
	@ApiModelProperty(value = "IP地址")
	private String ip;
	/**
	 * 请求完成时间
	 */
	
	@TableField(value = "request_end_time")
	@ApiModelProperty(value = "请求完成时间")
	private LocalDateTime requestEndTime;
	/**
	 * 响应状态码
	 */
	
	@TableField(value = "status_code")
	@ApiModelProperty(value = "响应状态码")
	private Integer statusCode;
	/**
	 * 请求方式
	 */
	
	@TableField(value = "request_method")
	@ApiModelProperty(value = "请求方式")
	private String requestMethod;
	/**
	 * 协议及版本
	 */
	
	@TableField(value = "server_protocol")
	@ApiModelProperty(value = "协议及版本")
	private String serverProtocol;
	/**
	 * 端口号
	 */
	
	@TableField(value = "server_port")
	@ApiModelProperty(value = "端口号")
	private String serverPort;
	/**
	 * 请求URI
	 */
	
	@TableField(value = "uri")
	@ApiModelProperty(value = "请求URI")
	private String uri;
	/**
	 * 请求参数
	 */
	
	@TableField(value = "args")
	@ApiModelProperty(value = "请求参数")
	private String args;
	/**
	 * 响应字节大小
	 */
	
	@TableField(value = "bytes_sent")
	@ApiModelProperty(value = "响应字节大小")
	private Long bytesSent;
	/**
	 * 请求花费时间(秒)
	 */
	
	@TableField(value = "request_time")
	@ApiModelProperty(value = "请求花费时间(秒)")
	private String requestTime;
	/**
	 * 客户端信息
	 */
	
	@TableField(value = "http_user_agent")
	@ApiModelProperty(value = "客户端信息")
	private String httpUserAgent;

	/**
	 * 纬度
	 */
	@TableField(value = "latitude")
	@ApiModelProperty(value = "纬度")
	private String latitude;
	/**
	 * 经度
	 */
	@TableField(value = "longitude")
	@ApiModelProperty(value = "经度")
	private String longitude;

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
