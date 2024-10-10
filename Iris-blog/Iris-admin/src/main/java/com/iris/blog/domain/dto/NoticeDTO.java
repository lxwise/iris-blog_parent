package com.iris.blog.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * @author lstar
 * @date: 2024-09
 * @description: NoticeDTO
 */
@Data
@ApiModel(value="NoticeDTO")
public class NoticeDTO {

	@ApiModelProperty(value = "通知类型:1系统通知2评论,3点赞")
	@NotNull(message = "通知类型不能为空")
	private Integer noticeType;

}
