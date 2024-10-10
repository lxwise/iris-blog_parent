package com.iris.blog.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

/**
 * @author lstar
 * @date: 2024-04
 * @description: 字典类型
 */
@Data
@ApiModel(value="字典类型")
public class DictTypeDTO implements Serializable {
	private static final long serialVersionUID = 1L;


	@ApiModelProperty(value = "id")
	private Long id;

	@ApiModelProperty(value = "字典类型")
	@NotBlank(message="{dictType.require}")
	private String dictType;

	@ApiModelProperty(value = "字典名称")
	@NotBlank(message="{dictName.require}")
	private String dictName;

	@ApiModelProperty(value = "备注")
	private String remark;

	@ApiModelProperty(value = "排序")
	@Min(value = 0, message = "{sort.number}")
	private Integer sort;

}
