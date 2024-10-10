package com.iris.blog.domain.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * @author lstar
 * @date: 2024-04
 * @description: 系统管理-用户表
 */
@Data
@ApiModel(value="系统管理-用户表")
public class UserVO implements Serializable {
	private static final long serialVersionUID = 1L;


	@ApiModelProperty(value = "主键ID")
	private Long id;

	@ApiModelProperty(value = "账号/用户名")
	private String username;

	@ApiModelProperty(value = "状态 0:禁用 1:正常")
	private Boolean status;

	@ApiModelProperty(value = "登录方式")
	private Integer loginType;

	@ApiModelProperty(value = "ip")
	private String ip;

	@ApiModelProperty(value = "ip地址")
	private String ipAddress;

	@ApiModelProperty(value = "省")
	private String province;

	@ApiModelProperty(value = "市")
	private String city;

	@ApiModelProperty(value = "区")
	private String region;

	@ApiModelProperty(value = "纬度")
	private String latitude;

	@ApiModelProperty(value = "经度")
	private String longitude;

	@ApiModelProperty(value = "操作系统")
	private String os;

	@ApiModelProperty(value = "最后登录时间")
	private LocalDateTime lastLoginTime;

	@ApiModelProperty(value = "创建时间")
	private LocalDateTime createTime;

	@ApiModelProperty(value = "更新时间")
	private LocalDateTime updateTime;

	@ApiModelProperty(value = "角色ID")
	private Integer roleId;

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

	@ApiModelProperty(value = "头像")
	private String avatar;

	@ApiModelProperty(value = "简介")
	private String info;

	@ApiModelProperty(value = "点赞文章集合")
	private Set<Object> articleLikeSet;

	@ApiModelProperty(value = "点赞评论集合")
	private Set<Object> commentLikeSet;

	@ApiModelProperty(value = "点赞说说集合")
	private Set<Object> talkLikeSet;
}
