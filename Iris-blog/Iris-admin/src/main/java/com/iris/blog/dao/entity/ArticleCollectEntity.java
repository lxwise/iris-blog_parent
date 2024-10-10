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
 * @description: 文章收藏表
 */
@Data
@TableName("t_article_collect")
@ApiModel(value="文章收藏表")
public class ArticleCollectEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键id
	 */
	@TableId(value = "id")
	@ApiModelProperty(value = "主键id")
	private Long id;
	/**
	 * 用户id
	 */
	
	@TableField(value = "user_id")
	@ApiModelProperty(value = "用户id")
	private Long userId;
	/**
	 * 文章id
	 */
	
	@TableField(value = "article_id")
	@ApiModelProperty(value = "文章id")
	private Long articleId;
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
