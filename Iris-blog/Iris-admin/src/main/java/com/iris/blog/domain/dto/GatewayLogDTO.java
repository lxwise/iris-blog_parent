package com.iris.blog.domain.dto;

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
public class GatewayLogDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "IP地址")
	private String ip;

	@ApiModelProperty(value = "响应状态码")
	private Integer statusCode;

	@ApiModelProperty(value = "请求方式")
	private String requestMethod;

	@ApiModelProperty(value = "端口号")
	private String serverPort;

	@ApiModelProperty(value = "请求URI")
	private String uri;

	@ApiModelProperty(value = "开始时间")
	private LocalDateTime createTimeStart;

	@ApiModelProperty(value = "结束时间")
	private LocalDateTime createTimeEnd;
}
