package com.iris.blog.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * @author lstar
 * @date: 2024-08
 * @description: 说说
 */
@Data
@ApiModel(value="说说")
public class TalkDTO implements Serializable {
	private static final long serialVersionUID = 1L;


	@ApiModelProperty(value = "说说id")
	private Long id;

	@ApiModelProperty(value = "用户id")
	private Long userId;

	@ApiModelProperty(value = "说说内容")
	private String talkContent;

	@ApiModelProperty(value = "是否置顶 (0否 1是)")
	private Integer isTop;

	@ApiModelProperty(value = "状态 (1公开  2私密)")
	private Integer status;
}
