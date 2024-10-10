package com.iris.blog.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

/**
 * @author lstar
 * @date: 2024-05
 * @description: 菜单权限表
 */
@Data
@ApiModel(value="菜单权限表")
public class MenuDTO implements Serializable {
	private static final long serialVersionUID = 1L;


	@ApiModelProperty(value = "主键ID")
	private Long id;

	@ApiModelProperty(value = "菜单名称")
	@NotBlank(message = "菜单名称不能为空")
	@Size(max = 50, message = "菜单名称长度不能超过50个字符")
	private String name;

	@ApiModelProperty(value = "权限标识,仅菜单类型为按钮时，才需要传递")
	@Size(max = 100)
	private String permission;

	@ApiModelProperty(value = "权限级别:1目录,2菜单,3按钮")
	@NotNull(message = "菜单类型不能为空")
	private Integer type;

	@ApiModelProperty(value = "排序")
	@NotNull(message = "排序不能为空")
	private Integer sort;

	@ApiModelProperty(value = "父ID")
	@NotNull(message = "父ID不能为空")
	private Long parentId;

	@ApiModelProperty(value = "路由地址,按钮不需要传")
	@Size(max = 200, message = "路由地址不能超过200字符")
	private String path;

	@ApiModelProperty(value = "菜单图标,按钮不需要传")
	private String icon;

	@ApiModelProperty(value = "组件路径,按钮不需要传")
	@Size(max = 200, message = "组件路径不能超过200字符")
	private String component;

	@ApiModelProperty(value = "组件名")
	private String componentName;

	@ApiModelProperty(value = "菜单状态:0禁用1启用")
	@NotNull(message = "状态不能为空")
	private Integer status;

	@ApiModelProperty(value = "是否可见")
	private Boolean visible;

	@ApiModelProperty(value = "是否缓存")
	private Boolean keepAlive;

	@ApiModelProperty(value = "是否总是显示")
	private Boolean alwaysShow;

}
