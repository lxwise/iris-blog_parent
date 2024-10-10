package com.iris.blog.domain.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author lstar
 * @date: 2024-04
 * @description: 系统管理-角色表 
 */
@Data
@ApiModel(value="系统管理-角色表 ")
public class RoleDTO implements Serializable {
	private static final long serialVersionUID = 1L;


	@ApiModelProperty(value = "主键ID")
	private Long id;

	@ApiModelProperty(value = "角色编码")
	private String code;

	@ApiModelProperty(value = "角色名称")
	private String name;

	@ApiModelProperty(value = "角色描述")
	private String remarks;
}
