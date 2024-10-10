package com.iris.blog.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * @author lstar
 * @date: 2024-04
 * @description: 字典数据
 */
@Data
@ApiModel(value="字典数据")
public class DictDataDTO implements Serializable {
	private static final long serialVersionUID = 1L;


	@ApiModelProperty(value = "id")
	private Long id;

	@ApiModelProperty(value = "字典类型")
	@NotNull(message="{dictType.require}")
	private String dictType;

	@ApiModelProperty(value = "字典标签")
	@NotNull(message="{dictLabel.require}")
	private String dictLabel;

	@ApiModelProperty(value = "字典值")
	@NotNull(message="{dictValue.require}")
	private String dictValue;

	@ApiModelProperty(value = "备注")
	private String remark;

	@ApiModelProperty(value = "排序")
	@Min(value = 0, message = "{sort.number}")
	private Integer sort;

}
