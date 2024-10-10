package com.iris.blog.domain.search;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author lstar
 * @date: 2024-04
 * @description: 菜单列表
 */
@Data
@ApiModel(value="菜单列表")
public class SearchMenuDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "菜单名称")
	private String name;

	@ApiModelProperty(value = "菜单状态:0禁用1启用")
	private Integer status;

}
