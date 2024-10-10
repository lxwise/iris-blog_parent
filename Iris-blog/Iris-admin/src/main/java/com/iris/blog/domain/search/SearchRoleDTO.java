package com.iris.blog.domain.search;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author lstar
 * @date: 2024-04
 * @description: 系统管理-角色表 
 */
@Data
@ApiModel(value="系统管理-角色表 ")
public class SearchRoleDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "角色编码")
	private String code;

	@ApiModelProperty(value = "角色名称")
	private String name;

}
