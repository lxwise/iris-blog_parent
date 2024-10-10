package com.iris.blog.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author lstar
 * @date: 2024-04
 * @description: 文章评论表
 */
@Data
@ApiModel(value="评论表")
public class UpdateCommentStatusDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "ID")
	@NotEmpty
	private List<Long> ids;

	@ApiModelProperty(value = "状态:0未审核,1审核通过,2驳回")
	@NotNull
	@Max(2)
	private Integer status;


}
