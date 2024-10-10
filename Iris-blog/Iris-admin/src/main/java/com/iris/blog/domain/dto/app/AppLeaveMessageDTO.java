package com.iris.blog.domain.dto.app;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author lstar
 * @date: 2024-04
 * @description: 添加留言DTO
 */
@Data
@ApiModel(value="添加留言DTO")
public class AppLeaveMessageDTO{

	@ApiModelProperty(value = "内容")
	@NotNull(message = "留言内容不能为空")
	private String content;

	@ApiModelProperty(value = "昵称")
	private String nickname;

	@ApiModelProperty(value = "头像")
	private String avatar;

}
