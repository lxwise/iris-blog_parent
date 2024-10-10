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
 * @description: 文章标签表
 */
@Data
@TableName("t_article_tag")
@ApiModel(value="文章标签表")
public class ArticleTagEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键id
	 */
	@TableId(value = "id")
	@ApiModelProperty(value = "主键id")
	private Long id;
	/**
	 * 名称
	 */
	
	@TableField(value = "name")
	@ApiModelProperty(value = "名称")
	private String name;
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
