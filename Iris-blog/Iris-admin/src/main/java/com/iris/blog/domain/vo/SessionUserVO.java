package com.iris.blog.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author lstar
 * @date: 2024-04
 * @description: SessionUserVO
 */
@Data
@ApiModel(value="SessionUserVO")
public class SessionUserVO implements Serializable {
	private static final long serialVersionUID = 1L;


	@ApiModelProperty(value = "主键ID")
	private Long id;
	@ApiModelProperty(value = "账号/用户名")
	private String username;
	@ApiModelProperty(value = "状态 0:禁用 1:正常")
	private Boolean status;
	@ApiModelProperty(value = "角色ID")
	private Integer roleId;
}
