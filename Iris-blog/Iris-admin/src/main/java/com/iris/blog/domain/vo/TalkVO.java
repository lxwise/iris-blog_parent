package com.iris.blog.domain.vo;

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
public class TalkVO implements Serializable {
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

	@ApiModelProperty(value = "点赞量")
	private Integer likeCount;

	@ApiModelProperty(value = "评论量")
	private Integer commentCount;

	@ApiModelProperty(value = "昵称")
	private String nickname;

	@ApiModelProperty(value = "头像")
	private String avatar;

	@ApiModelProperty(value = "创建时间")
	private LocalDateTime createTime;

	@ApiModelProperty(value = "修改时间")
	private LocalDateTime updateTime;

}
