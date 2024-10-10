package com.iris.blog.dao.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * @author lstar
 * @date: 2024-04
 * @description: 参数管理
 */
@Data
@TableName("t_sys_config")
@ApiModel(value="参数管理")
public class SysConfigEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId(value = "id")
	@ApiModelProperty(value = "id")
	private Long id;
	/**
	 * 参数编码
	 */
	
	@TableField(value = "param_code")
	@ApiModelProperty(value = "参数编码")
	private String paramCode;
	/**
	 * 参数值
	 */
	
	@TableField(value = "param_value")
	@ApiModelProperty(value = "参数值")
	private String paramValue;
	/**
	 * 类型   0：系统参数   1：非系统参数
	 */
	
	@TableField(value = "param_type")
	@ApiModelProperty(value = "类型   0：系统参数   1：非系统参数")
	private Boolean paramType;
	/**
	 * 备注
	 */
	
	@TableField(value = "remark")
	@ApiModelProperty(value = "备注")
	private String remark;
	/**
	 * 状态  0：不使用    1：使用
	 */
	
	@TableField(value = "status")
	@ApiModelProperty(value = "状态  0：不使用    1：使用")
	private Boolean status;
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
