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
 * @date: 2024-05
 * @description: 菜单权限表
 */
@Data
@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_menu")
@ApiModel(value="菜单权限表")
public class MenuEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键ID
	 */
	@TableId(value = "id")
	@ApiModelProperty(value = "主键ID")
	private Long id;
	/**
	 * 菜单名称
	 */
	
	@TableField(value = "name")
	@ApiModelProperty(value = "菜单名称")
	private String name;
	/**
	 * 权限标识
	 */
	
	@TableField(value = "permission")
	@ApiModelProperty(value = "权限标识")
	private String permission;
	/**
	 * 权限级别:1目录,2菜单,3按钮
	 */
	
	@TableField(value = "type")
	@ApiModelProperty(value = "权限级别:1目录,2菜单,3按钮")
	private Integer type;
	/**
	 * 排序
	 */
	
	@TableField(value = "sort")
	@ApiModelProperty(value = "排序")
	private Integer sort;
	/**
	 * 父ID
	 */
	
	@TableField(value = "parent_id")
	@ApiModelProperty(value = "父ID")
	private Long parentId;
	/**
	 * 路由地址
	 */
	
	@TableField(value = "path")
	@ApiModelProperty(value = "路由地址")
	private String path;
	/**
	 * 菜单图标
	 */
	
	@TableField(value = "icon")
	@ApiModelProperty(value = "菜单图标")
	private String icon;
	/**
	 * 组件路径
	 */
	
	@TableField(value = "component")
	@ApiModelProperty(value = "组件路径")
	private String component;
	/**
	 * 组件名
	 */
	
	@TableField(value = "component_name")
	@ApiModelProperty(value = "组件名")
	private String componentName;
	/**
	 * 菜单状态
	 */
	
	@TableField(value = "status")
	@ApiModelProperty(value = "菜单状态:0禁用1启用")
	private Integer status;
	/**
	 * 是否可见
	 */
	
	@TableField(value = "visible")
	@ApiModelProperty(value = "是否可见")
	private Integer visible;
	/**
	 * 是否缓存
	 */
	
	@TableField(value = "keep_alive")
	@ApiModelProperty(value = "是否缓存")
	private Integer keepAlive;
	/**
	 * 是否总是显示
	 */
	
	@TableField(value = "always_show")
	@ApiModelProperty(value = "是否总是显示")
	private Integer alwaysShow;
	/**
	 * 创建者
	 */
	
	@TableField(value = "creator")
	@ApiModelProperty(value = "创建者")
	private String creator;
	/**
	 * 创建时间
	 */
	
	@TableField(value = "create_time", fill = FieldFill.INSERT)
	@ApiModelProperty(value = "创建时间")
	private LocalDateTime createTime;
	/**
	 * 更新者
	 */
	
	@TableField(value = "updater")
	@ApiModelProperty(value = "更新者")
	private String updater;
	/**
	 * 更新时间
	 */
	
	@TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
	@ApiModelProperty(value = "更新时间")
	private LocalDateTime updateTime;
}
