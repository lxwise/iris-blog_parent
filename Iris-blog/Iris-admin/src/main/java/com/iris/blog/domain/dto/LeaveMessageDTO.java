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
 * @description: 用户留言表
 */
@Data
@ApiModel(value="用户留言表")
public class LeaveMessageDTO implements Serializable {
	private static final long serialVersionUID = 1L;


	@ApiModelProperty(value = "主键ID")
	private Long id;

	@ApiModelProperty(value = "内容")
	private String content;

	@ApiModelProperty(value = "昵称")
	private String nickname;

	@ApiModelProperty(value = "头像")
	private String avatar;

	@ApiModelProperty(value = "ip")
	private String ip;

	@ApiModelProperty(value = "ip地址")
	private String ipAddress;

	@ApiModelProperty(value = "操作系统")
	private String os;

	@ApiModelProperty(value = "状态:0未审核,1审核通过,2驳回")
	private Integer status;

	@ApiModelProperty(value = "创建时间")
	private LocalDateTime createTime;

	@ApiModelProperty(value = "修改时间")
	private LocalDateTime updateTime;

}
