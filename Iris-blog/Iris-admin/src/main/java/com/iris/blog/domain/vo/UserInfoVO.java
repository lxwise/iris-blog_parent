package com.iris.blog.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * @author lstar
 * @date: 2024-04
 * @description: 用户信息表
 */
@Data
@ApiModel(value="用户信息表")
public class UserInfoVO implements Serializable {
	private static final long serialVersionUID = 1L;


	@ApiModelProperty(value = "用户ID")
	private Long id;

	@ApiModelProperty(value = "联系电话")
	private String phone;

	@ApiModelProperty(value = "联系qq")
	private String qq;

	@ApiModelProperty(value = "联系wx")
	private String wx;

	@ApiModelProperty(value = "联系邮箱")
	private String email;

	@ApiModelProperty(value = "昵称")
	private String nickname;

	@ApiModelProperty(value = "性别:0女,1男,2未知")
	private Integer sex;

	@ApiModelProperty(value = "简介")
	private String info;

	@ApiModelProperty(value = "头像")
	private String avatar;

	@ApiModelProperty(value = "是否禁用:0否,1是")
	private Integer isDisable;

	@ApiModelProperty(value = "个人中心背景图")
	private String backImage;

	@ApiModelProperty(value = "创建时间")
	private LocalDateTime createTime;

	@ApiModelProperty(value = "更新时间")
	private LocalDateTime updateTime;

}
