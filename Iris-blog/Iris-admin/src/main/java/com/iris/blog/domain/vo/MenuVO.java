package com.iris.blog.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * @author lstar
 * @date: 2024-05
 * @description: 菜单权限表
 */
@Data
@ApiModel(value="菜单权限表")
public class MenuVO implements Serializable {
	private static final long serialVersionUID = 1L;


	@ApiModelProperty(value = "主键ID")
	private Long id;

	@ApiModelProperty(value = "菜单名称")
	private String name;

	@ApiModelProperty(value = "权限标识")
	private String permission;

	@ApiModelProperty(value = "权限级别:1目录,2菜单,3按钮")
	private Integer type;

	@ApiModelProperty(value = "排序")
	private Integer sort;

	@ApiModelProperty(value = "父ID")
	private Long parentId;

	@ApiModelProperty(value = "路由地址")
	private String path;

	@ApiModelProperty(value = "菜单图标")
	private String icon;

	@ApiModelProperty(value = "组件路径")
	private String component;

	@ApiModelProperty(value = "组件名")
	private String componentName;

	@ApiModelProperty(value = "菜单状态:0禁用1启用")
	private Integer status;

	@ApiModelProperty(value = "是否可见")
	private Integer visible;

	@ApiModelProperty(value = "是否缓存")
	private Integer keepAlive;

	@ApiModelProperty(value = "是否总是显示")
	private Integer alwaysShow;

	@ApiModelProperty(value = "创建者")
	private String creator;

	@ApiModelProperty(value = "创建时间")
	private LocalDateTime createTime;

	@ApiModelProperty(value = "更新者")
	private String updater;

	@ApiModelProperty(value = "更新时间")
	private LocalDateTime updateTime;

}
