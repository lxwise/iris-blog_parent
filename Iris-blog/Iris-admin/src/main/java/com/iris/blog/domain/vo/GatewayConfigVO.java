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
 * @description: 网关配置
 */
@Data
@ApiModel(value="网关配置")
public class GatewayConfigVO implements Serializable {
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

	@ApiModelProperty(value = "创建时间")
	private LocalDateTime createTime;

	@ApiModelProperty(value = "修改时间")
	private LocalDateTime updateTime;

}
