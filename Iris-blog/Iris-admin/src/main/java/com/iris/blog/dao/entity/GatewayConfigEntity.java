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
 * @date: 2024-06
 * @description: 网关配置
 */
@Data
@TableName("t_gateway_config")
@ApiModel(value="网关配置")
public class GatewayConfigEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键id
	 */
	@TableId(value = "id")
	@ApiModelProperty(value = "主键id")
	private Long id;
	/**
	 * 配置名
	 */
	
	@TableField(value = "name")
	@ApiModelProperty(value = "配置名")
	private String name;
	/**
	 * 配置路径
	 */

	@TableField(value = "path")
	@ApiModelProperty(value = "配置路径")
	private String path;
	/**
	 * 文件名
	 */
	
	@TableField(value = "file_name")
	@ApiModelProperty(value = "文件名")
	private String fileName;
	/**
	 * 读取位置
	 */
	
	@TableField(value = "position")
	@ApiModelProperty(value = "读取位置")
	private Long position;
	/**
	 * 创建时间
	 */
	
	@TableField(value = "create_time", fill = FieldFill.INSERT)
	@ApiModelProperty(value = "创建时间")
	private LocalDateTime createTime;
	/**
	 * 修改时间
	 */
	
	@TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
	@ApiModelProperty(value = "修改时间")
	private LocalDateTime updateTime;
}
