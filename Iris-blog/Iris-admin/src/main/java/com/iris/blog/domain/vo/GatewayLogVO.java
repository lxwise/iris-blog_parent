package com.iris.blog.domain.vo;

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
@ApiModel(value="网关日志")
public class GatewayLogVO implements Serializable {
	private static final long serialVersionUID = 1L;


	@ApiModelProperty(value = "主键id")
	private Long id;

	@ApiModelProperty(value = "IP地址")
	private String ip;

	@ApiModelProperty(value = "请求完成时间")
	private LocalDateTime requestEndTime;

	@ApiModelProperty(value = "响应状态码")
	private Integer statusCode;

	@ApiModelProperty(value = "请求方式")
	private String requestMethod;

	@ApiModelProperty(value = "协议及版本")
	private String serverProtocol;

	@ApiModelProperty(value = "端口号")
	private String serverPort;

	@ApiModelProperty(value = "请求URI")
	private String uri;

	@ApiModelProperty(value = "请求参数")
	private String args;

	@ApiModelProperty(value = "响应字节大小")
	private Long bytesSent;

	@ApiModelProperty(value = "请求花费时间(秒)")
	private String requestTime;

	@ApiModelProperty(value = "客户端信息")
	private String httpUserAgent;

	@ApiModelProperty(value = "纬度")
	private String latitude;

	@ApiModelProperty(value = "经度")
	private String longitude;

	@ApiModelProperty(value = "创建时间")
	private LocalDateTime createTime;

	@ApiModelProperty(value = "修改时间")
	private LocalDateTime updateTime;

}
