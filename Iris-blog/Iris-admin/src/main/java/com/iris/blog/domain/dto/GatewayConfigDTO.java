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
 * @description: 网关配置
 */
@Data
@ApiModel(value="网关配置")
public class GatewayConfigDTO implements Serializable {
	private static final long serialVersionUID = 1L;


	@ApiModelProperty(value = "主键id")
	private Long id;

	@ApiModelProperty(value = "配置名")
	private String name;

	@ApiModelProperty(value = "配置路径")
	private String path;

	@ApiModelProperty(value = "文件名")
	private String fileName;

	@ApiModelProperty(value = "读取位置")
	private Long position;
}
