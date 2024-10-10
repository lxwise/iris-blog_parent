package com.iris.blog.domain.vo;

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
@ApiModel(value="文章收藏表")
public class ArticleCollectVO implements Serializable {
	private static final long serialVersionUID = 1L;


	@ApiModelProperty(value = "主键id")
	private Long id;

	@ApiModelProperty(value = "用户id")
	private Long userId;

	@ApiModelProperty(value = "文章id")
	private Long articleId;

	@ApiModelProperty(value = "创建时间")
	private LocalDateTime createTime;

	@ApiModelProperty(value = "修改时间")
	private LocalDateTime updateTime;

}
