package com.iris.blog.domain.search;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author lstar
 * @date: 2024-04
 * @description: 文章评论表
 */
@Data
@ApiModel(value="SearchCommentDTO")
public class SearchCommentDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "评论类型:1文章,2说说,3友链")
	private Integer commentType;

	@ApiModelProperty(value = "名称")
	private String name;

	@ApiModelProperty(value = "状态:0未审核,1审核通过,2驳回")
	private Integer status;

	@ApiModelProperty("创建时间开始")
	private LocalDateTime createTimeStart;

	@ApiModelProperty("创建时间结束")
	private LocalDateTime createTimeEnd;

}
