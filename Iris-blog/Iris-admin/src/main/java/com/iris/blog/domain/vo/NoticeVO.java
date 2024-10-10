package com.iris.blog.domain.vo;

import com.iris.blog.common.enums.NoticeTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * @author lstar
 * @date: 2024-09
 * @description: NoticeVO
 */
@Data
@ApiModel(value="NoticeVO")
public class NoticeVO {

	@ApiModelProperty(value = "主键id")
	private Long id;

	@ApiModelProperty(value = "发送用户id")
	private Long toUserId;

	@ApiModelProperty(value = "发送用户昵称")
	private String toUserNickname;

	@ApiModelProperty(value = "发送用户头像")
	private String toUserAvatar;

	@ApiModelProperty(value = "接收用户id")
	private Long fromUserId;

	@ApiModelProperty(value = "通知内容")
	private String content;

	@ApiModelProperty(value = "省")
	private String province;

	@ApiModelProperty(value = "是否已读:0未读,1已读")
	private Integer status;

	@ApiModelProperty(value = "通知类型:1系统通知2评论,3点赞")
	private Integer noticeType;

	@ApiModelProperty(value = "通知路径:talk/article/friend")
	private String noticeTypePath;

	@ApiModelProperty(value = "通知类型id:系统通知null")
	private Long typeId;

	@ApiModelProperty(value = "创建时间")
	private LocalDateTime createTime;

}
