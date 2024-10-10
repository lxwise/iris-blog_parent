package com.iris.blog.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * @author lstar
 * @date: 2024-05
 * @description: 存储文件表
 */
@Data
@ApiModel(value="存储文件表")
public class OssFileVO implements Serializable {
	private static final long serialVersionUID = 1L;


	@ApiModelProperty(value = "文件id")
	private Long id;

	@ApiModelProperty(value = "文件名")
	private String name;

	@ApiModelProperty(value = "文件URL")
	private String url;

	@ApiModelProperty(value = "文件路径")
	private String filePath;

	@ApiModelProperty(value = "文件类型")
	private String type;

	@ApiModelProperty(value = "文件大小")
	private Long size;

	@ApiModelProperty(value = "配置类型")
	private Integer configType;

	@ApiModelProperty(value = "创建者")
	private String creator;

	@ApiModelProperty(value = "创建时间")
	private LocalDateTime createTime;

}
