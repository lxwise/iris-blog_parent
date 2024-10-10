package com.iris.blog.domain.dto.app;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * @author lstar
 * @date: 2024-04
 * @description: 修改用户DTO
 */
@Data
@ApiModel(value="修改用户DTO")
public class AppUserDTO {

	@ApiModelProperty(value = "昵称")
	@NotNull(message = "昵称不能为空")
	@Size(min = 1, max = 32, message = "昵称不能为空长度在1-32之间")
	private String nickname;

	@ApiModelProperty(value = "联系电话")
	@Size(min = 11, max = 11, message = "联系电话格式错误")
	private String phone;

	@ApiModelProperty(value = "联系qq")
	@Size(min = 1, max = 32, message = "联系qq长度在5-10之间")
	private String qq;

	@ApiModelProperty(value = "联系wx")
	@Size(min = 1, max = 32, message = "联系微信长度在1-32之间")
	private String wx;

	@ApiModelProperty(value = "联系邮箱")
	@Email(message = "邮箱格式不正确")
	private String email;

	@ApiModelProperty(value = "性别:0女,1男,2未知")
	private Integer sex;

	@ApiModelProperty(value = "简介")
	@Size(min = 1, max = 256, message = "简介长度在1-256之间")
	private String info;
}
