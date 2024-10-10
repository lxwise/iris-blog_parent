package com.iris.blog.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author lstar
 * @date: 2024-05
 * @description: 菜单权限表
 */
@Data
@ApiModel(value="菜单权限表")
public class MenuSimpleVO implements Serializable {
	private static final long serialVersionUID = 1L;


	@ApiModelProperty(value = "主键ID")
	private Long id;

	@ApiModelProperty(value = "菜单名称")
	private String name;

	@ApiModelProperty(value = "权限级别:1目录,2菜单,3按钮")
	private Integer type;

	@ApiModelProperty(value = "父ID")
	private Long parentId;

}
