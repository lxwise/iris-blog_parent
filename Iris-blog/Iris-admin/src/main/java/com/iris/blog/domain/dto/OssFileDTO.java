package com.iris.blog.domain.dto;

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
public class OssFileDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "文件名")
	private String name;

	@ApiModelProperty(value = "文件URL")
	private String url;

	@ApiModelProperty(value = "文件类型")
	private String type;

	@ApiModelProperty("创建时间开始")
	private LocalDateTime createTimeStart;

	@ApiModelProperty("创建时间结束")
	private LocalDateTime createTimeEnd;

}
