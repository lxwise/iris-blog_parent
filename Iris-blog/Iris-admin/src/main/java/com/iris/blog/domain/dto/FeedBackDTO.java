package com.iris.blog.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * @author lstar
 * @date: 2024-04
 * @description: 用户反馈表
 */
@Data
@ApiModel(value="用户反馈表")
public class FeedBackDTO implements Serializable {
	private static final long serialVersionUID = 1L;


	@ApiModelProperty(value = "主键ID")
	private Long id;

	@ApiModelProperty(value = "用户id")
	private Long userId;

	@ApiModelProperty(value = "标题")
	private String title;

	@ApiModelProperty(value = "详细内容")
	private String content;

	@ApiModelProperty(value = "图片地址")
	private String imgUrl;

	@ApiModelProperty(value = "反馈类型 0:需求 1：缺陷")
	private Integer backType;

	@ApiModelProperty(value = "状态 0未解决 1解决")
	private Integer status;

}
