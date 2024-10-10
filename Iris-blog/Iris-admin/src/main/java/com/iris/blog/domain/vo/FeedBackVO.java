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
 * @description: 用户反馈表
 */
@Data
@ApiModel(value="用户反馈表")
public class FeedBackVO implements Serializable {
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
	private Boolean backType;

	@ApiModelProperty(value = "状态 0未解决 1解决")
	private Boolean status;

	@ApiModelProperty(value = "ip")
	private String ip;

	@ApiModelProperty(value = "ip地址")
	private String ipAddress;

	@ApiModelProperty(value = "操作系统")
	private String os;

	@ApiModelProperty(value = "创建时间")
	private LocalDateTime createTime;

	@ApiModelProperty(value = "修改时间")
	private LocalDateTime updateTime;

}
