package com.iris.blog.dao.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * @author lstar
 * @date: 2024-05
 * @description: 存储文件表
 */
@Data
@TableName("t_oss_file")
@ApiModel(value="存储文件表")
public class OssFileEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 文件id
	 */
	@TableId(value = "id")
	@ApiModelProperty(value = "文件id")
	private Long id;
	/**
	 * 文件名
	 */
	
	@TableField(value = "name",condition = SqlCondition.LIKE)
	@ApiModelProperty(value = "文件名")
	private String name;
	/**
	 * 文件URL
	 */
	
	@TableField(value = "url",condition = SqlCondition.LIKE)
	@ApiModelProperty(value = "文件URL")
	private String url;

	/**
	 * 文件路径
	 */

	@TableField(value = "file_path",condition = SqlCondition.LIKE)
	@ApiModelProperty(value = "文件路径")
	private String filePath;
	/**
	 * 文件类型
	 */
	
	@TableField(value = "type")
	@ApiModelProperty(value = "文件类型")
	private String type;
	/**
	 * 文件大小
	 */
	
	@TableField(value = "size")
	@ApiModelProperty(value = "文件大小")
	private Long size;
	/**
	 * 配置类型
	 */
	
	@TableField(value = "config_type")
	@ApiModelProperty(value = "配置类型")
	private Integer configType;
	/**
	 * 创建者
	 */
	
	@TableField(value = "creator")
	@ApiModelProperty(value = "创建者")
	private String creator;
	/**
	 * 创建时间
	 */
	
	@TableField(value = "create_time", fill = FieldFill.INSERT)
	@ApiModelProperty(value = "创建时间")
	private LocalDateTime createTime;
}
