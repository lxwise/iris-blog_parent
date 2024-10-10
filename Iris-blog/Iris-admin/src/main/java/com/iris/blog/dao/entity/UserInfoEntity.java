package com.iris.blog.dao.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
@TableName("t_user_info")
@ApiModel(value="用户信息表")
public class UserInfoEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 用户ID
	 */
	@TableId(value = "id")
	@ApiModelProperty(value = "用户ID")
	private Long id;
	/**
	 * 联系电话
	 */
	
	@TableField(value = "phone")
	@ApiModelProperty(value = "联系电话")
	private String phone;
	/**
	 * 联系qq
	 */
	
	@TableField(value = "qq")
	@ApiModelProperty(value = "联系qq")
	private String qq;
	/**
	 * 联系wx
	 */
	
	@TableField(value = "wx")
	@ApiModelProperty(value = "联系wx")
	private String wx;
	/**
	 * 联系邮箱
	 */
	
	@TableField(value = "email")
	@ApiModelProperty(value = "联系邮箱")
	private String email;
	/**
	 * 昵称
	 */
	
	@TableField(value = "nickname")
	@ApiModelProperty(value = "昵称")
	private String nickname;
	/**
	 * 性别:0女,1男,2未知
	 */
	
	@TableField(value = "sex")
	@ApiModelProperty(value = "性别:0女,1男,2未知")
	private Integer sex;
	/**
	 * 简介
	 */
	
	@TableField(value = "info")
	@ApiModelProperty(value = "简介")
	private String info;
	/**
	 * 头像
	 */
	
	@TableField(value = "avatar")
	@ApiModelProperty(value = "头像")
	private String avatar;
	/**
	 * 是否禁用:0否,1是
	 */
	
	@TableField(value = "is_disable")
	@ApiModelProperty(value = "是否禁用:0否,1是")
	private Integer isDisable;
	/**
	 * 个人中心背景图
	 */
	
	@TableField(value = "back_image")
	@ApiModelProperty(value = "个人中心背景图")
	private String backImage;
	/**
	 * 创建时间
	 */
	
	@TableField(value = "create_time", fill = FieldFill.INSERT)
	@ApiModelProperty(value = "创建时间")
	private LocalDateTime createTime;
	/**
	 * 更新时间
	 */
	
	@TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
	@ApiModelProperty(value = "更新时间")
	private LocalDateTime updateTime;
}
