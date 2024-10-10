package com.iris.blog.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * @author lstar
 * @date: 2024-04
 * @description: 字典数据
 */
@Data
@ApiModel(value="字典数据")
public class DictDataVO implements Serializable {
	private static final long serialVersionUID = 1L;


	@ApiModelProperty(value = "id")
	private Long id;

	@ApiModelProperty(value = "字典类型")
	private String dictType;

	@ApiModelProperty(value = "字典标签")
	private String dictLabel;

	@ApiModelProperty(value = "字典值")
	private String dictValue;

	@ApiModelProperty(value = "备注")
	private String remark;

	@ApiModelProperty(value = "排序")
	private Integer sort;

	@ApiModelProperty(value = "创建时间")
	private LocalDateTime createTime;

	@ApiModelProperty(value = "更新时间")
	private LocalDateTime updateTime;

}
