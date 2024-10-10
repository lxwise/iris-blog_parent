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
 * @description: 系统管理-角色-权限关联表 
 */
@Data
@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_role_menu")
@ApiModel(value="系统管理-角色-权限关联表 ")
public class RoleMenuEntity implements Serializable {
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
	 * 菜单ID
	 */
	
	@TableField(value = "menu_id")
	@ApiModelProperty(value = "菜单ID")
	private Long menuId;
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
