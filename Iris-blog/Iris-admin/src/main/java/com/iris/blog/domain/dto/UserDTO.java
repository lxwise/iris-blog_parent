package com.iris.blog.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author lstar
 * @date: 2024-04
 * @description: 系统管理-用户表
 */
@Data
@ApiModel(value="系统管理-用户表")
public class UserDTO implements Serializable {
	private static final long serialVersionUID = 1L;


	@ApiModelProperty(value = "主键ID")
	private Long id;

	@ApiModelProperty(name = "avatar", value = "头像")
	private String avatar;

	@ApiModelProperty(value = "账号/用户名")
	@NotNull(message = "用户名不能为空")
	@Size(min = 1, max = 32, message = "用户名长度在1-32之间")
	private String username;

	@ApiModelProperty(value = "登录密码")
	@Size(min = 1, max = 32, message = "登录密码长度在1-32之间")
	private String password;

	@ApiModelProperty(value = "昵称")
	@NotNull(message = "昵称不能为空")
	@Size(min = 1, max = 32, message = "昵称不能为空长度在1-32之间")
	private String nickname;

	@ApiModelProperty(value = "联系电话")
	private String phone;

	@ApiModelProperty(value = "联系qq")
	private String qq;

	@ApiModelProperty(value = "联系wx")
	private String wx;

	@ApiModelProperty(value = "联系邮箱")
	private String email;

	@ApiModelProperty(value = "性别:0女,1男,2未知")
	private Integer sex;

	@ApiModelProperty(value = "简介")
	private String info;
}
