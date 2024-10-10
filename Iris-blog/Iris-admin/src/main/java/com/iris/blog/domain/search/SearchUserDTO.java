package com.iris.blog.domain.search;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author lstar
 * @date: 2024-04
 * @description: 系统管理-用户
 */
@Data
@ApiModel(value="系统管理-用户")
public class SearchUserDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "账号/用户名")
	private String username;

	@ApiModelProperty(value = "电话号码")
	private String phone;

	@ApiModelProperty(value = "状态 0:禁用 1:正常")
	private Boolean status;

	@ApiModelProperty(value = "登录方式")
	private Integer loginType;

}
