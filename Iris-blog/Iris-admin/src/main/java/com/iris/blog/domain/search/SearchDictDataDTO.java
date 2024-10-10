package com.iris.blog.domain.search;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author lstar
 * @date: 2024-04
 * @description: 字典数据
 */
@Data
@ApiModel(value="字典数据")
public class SearchDictDataDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "字典类型")
	private String dictType;

	@ApiModelProperty(value = "字典标签")
	private String dictLabel;

	@ApiModelProperty(value = "字典值")
	private String dictValue;

}
