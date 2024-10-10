package com.iris.blog.dao.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * @author lstar
 * @date: 2024-04
 * @description: 字典类型
 */
@Data
@TableName("t_dict_type")
@ApiModel(value="字典类型")
public class DictTypeEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId(value = "id")
	@ApiModelProperty(value = "id")
	private Long id;
	/**
	 * 字典类型
	 */
	
	@TableField(value = "dict_type")
	@ApiModelProperty(value = "字典类型")
	private String dictType;
	/**
	 * 字典名称
	 */
	
	@TableField(value = "dict_name")
	@ApiModelProperty(value = "字典名称")
	private String dictName;
	/**
	 * 备注
	 */
	
	@TableField(value = "remark")
	@ApiModelProperty(value = "备注")
	private String remark;
	/**
	 * 排序
	 */
	
	@TableField(value = "sort")
	@ApiModelProperty(value = "排序")
	private Integer sort;
	/**
	 * 创建时间
	 */

	@TableField(value = "create_time", fill = FieldFill.INSERT)
	@ApiModelProperty(value = "创建时间")
	private LocalDateTime createTime;
	/**
	 * 更新时间
	 */

	@TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
	@ApiModelProperty(value = "更新时间")
	private LocalDateTime updateTime;
}
