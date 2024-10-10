package com.iris.blog.domain.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * @author lstar
 * @date: 2024-04
 * @description: 文章评论表
 */
@Data
@ApiModel(value="文章评论表")
public class CommentVO implements Serializable {
	private static final long serialVersionUID = 1L;


	@ApiModelProperty(value = "ID")
	private Long id;

	@ApiModelProperty(value = "评论人ID")
	private Long userId;

	@ApiModelProperty(value = "评论类型:1文章,2说说,3友链")
	private Integer commentType;

	@ApiModelProperty(value = "类型id")
	private Long typeId;

	@ApiModelProperty(value = "昵称")
	private String nickname;

	@ApiModelProperty(value = "头像")
	private String avatar;

	@ApiModelProperty(value = "内容")
	private String content;

	@ApiModelProperty(value = "回复人id")
	private Long replyUserId;

	@ApiModelProperty(value = "回复人")
	private String replyNickname;

	@ApiModelProperty(value = "父id")
	private Integer parentId;

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
