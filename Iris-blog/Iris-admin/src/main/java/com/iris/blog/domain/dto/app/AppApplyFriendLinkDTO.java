package com.iris.blog.domain.dto.app;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

/**
 * @author lstar
 * @date: 2024-04
 * @description: 申请友链DTO
 */
@Data
@ApiModel(value="申请友链DTO")
public class AppApplyFriendLinkDTO {

	@ApiModelProperty(value = "网站名称")
	@NotNull(message = "网站名称不能为空")
	private String name;

	@ApiModelProperty(value = "网站地址")
	@NotNull(message = "网站地址不能为空")
	private String url;

	@ApiModelProperty(value = "网站logo")
	@NotNull(message = "网站logo不能为空")
	private String image;

	@ApiModelProperty(value = "网站描述")
	@NotNull(message = "网站描述不能为空")
	private String info;

	@ApiModelProperty(value = "联系邮箱")
	@NotNull(message = "联系邮箱不能为空")
	@Email(message = "邮箱格式不正确")
	private String email;

}