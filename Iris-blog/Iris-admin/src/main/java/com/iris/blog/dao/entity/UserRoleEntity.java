package com.iris.blog.dao.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;

import lombok.*;

import java.time.LocalDateTime;

/**
 * @author lstar
 * @date: 2024-04
 * @description: 系统管理-用户角色关联表 
 */
@Data
@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_user_role")
@ApiModel(value="系统管理-用户角色关联表 ")
public class UserRoleEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键ID
	 */
	@TableId(value = "id")
	@ApiModelProperty(value = "主键ID")
	private Long id;
	/**
	 * 角色ID
	 */
	
	@TableField(value = "role_id")
	@ApiModelProperty(value = "角色ID")
	private Long roleId;
	/**
	 * 用户ID
	 */
	
	@TableField(value = "user_id")
	@ApiModelProperty(value = "用户ID")
	private Long userId;
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
