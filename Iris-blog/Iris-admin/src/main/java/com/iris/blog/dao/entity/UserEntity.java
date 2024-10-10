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
 * @description: 系统管理-用户表
 */
@Data
@TableName("t_user")
@ApiModel(value="系统管理-用户表")
public class UserEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键ID
	 */
	@TableId(value = "id")
	@ApiModelProperty(value = "主键ID")
	private Long id;
	/**
	 * 账号/用户名
	 */
	
	@TableField(value = "username")
	@ApiModelProperty(value = "账号/用户名")
	private String username;
	/**
	 * 登录密码
	 */
	
	@TableField(value = "password")
	@ApiModelProperty(value = "登录密码")
	private String password;
	/**
	 * 状态 0:禁用 1:正常
	 */
	
	@TableField(value = "status")
	@ApiModelProperty(value = "状态 0:禁用 1:正常")
	private Boolean status;
	/**
	 * 登录方式:1邮箱,2QQ,3微博,4码云,5github
	 */
	
	@TableField(value = "login_type")
	@ApiModelProperty(value = "登录方式")
	private Integer loginType;
	/**
	 * ip
	 */
	
	@TableField(value = "ip")
	@ApiModelProperty(value = "ip")
	private String ip;
	/**
	 * ip地址
	 */
	
	@TableField(value = "ip_address")
	@ApiModelProperty(value = "ip地址")
	private String ipAddress;
	/**
	 * 省
	 */

	@TableField(value = "province")
	@ApiModelProperty(value = "省")
	private String province;
	/**
	 * 市
	 */

	@TableField(value = "city")
	@ApiModelProperty(value = "市")
	private String city;
	/**
	 * 区
	 */

	@TableField(value = "region")
	@ApiModelProperty(value = "区")
	private String region;

	/**
	 * 纬度
	 */
	@TableField(value = "latitude")
	@ApiModelProperty(value = "纬度")
	private String latitude;
	/**
	 * 经度
	 */
	@TableField(value = "longitude")
	@ApiModelProperty(value = "经度")
	private String longitude;
	/**
	 * 操作系统
	 */
	
	@TableField(value = "os")
	@ApiModelProperty(value = "操作系统")
	private String os;
	/**
	 * 最后登录时间
	 */
	
	@TableField(value = "last_login_time")
	@ApiModelProperty(value = "最后登录时间")
	private LocalDateTime lastLoginTime;
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
