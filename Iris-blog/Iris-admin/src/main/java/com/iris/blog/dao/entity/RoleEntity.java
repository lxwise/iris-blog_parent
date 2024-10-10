package com.iris.blog.dao.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;

import lombok.*;

import java.time.LocalDateTime;

/**
 * @author lstar
 * @date: 2024-04
 * @description: 系统管理-角色表 
 */
@Data
@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_role")
@ApiModel(value="系统管理-角色表 ")
public class RoleEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键ID
	 */
	@TableId(value = "id")
	@ApiModelProperty(value = "主键ID")
	private Long id;
	/**
	 * 角色编码
	 */
	
	@TableField(value = "code")
	@ApiModelProperty(value = "角色编码")
	private String code;
	/**
	 * 角色名称
	 */
	
	@TableField(value = "name",condition = SqlCondition.LIKE)
	@ApiModelProperty(value = "角色名称")
	private String name;
	/**
	 * 角色描述
	 */
	
	@TableField(value = "remarks")
	@ApiModelProperty(value = "角色描述")
	private String remarks;
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
