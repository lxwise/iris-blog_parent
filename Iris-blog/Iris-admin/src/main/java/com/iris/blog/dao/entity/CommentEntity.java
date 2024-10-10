package com.iris.blog.dao.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author lstar
 * @date: 2024-04
 * @description: 评论表
 */
@Data
@TableName("t_comment")
@ApiModel(value="评论表")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	@TableId(value = "id")
	@ApiModelProperty(value = "ID")
	private Long id;
	/**
	 * 评论人ID
	 */
	
	@TableField(value = "user_id")
	@ApiModelProperty(value = "评论人ID")
	private Long userId;
	/**
	 * 评论类型:1文章,2说说,3友链
	 */

	@TableField(value = "comment_type")
	@ApiModelProperty(value = "评论类型:1文章,2说说,3友链")
	private Integer commentType;
	/**
	 * 类型id
	 */
	
	@TableField(value = "type_id")
	@ApiModelProperty(value = "类型id")
	private Integer typeId;
	/**
	 * 内容
	 */
	
	@TableField(value = "content")
	@ApiModelProperty(value = "内容")
	private String content;
	/**
	 * 回复人id
	 */
	
	@TableField(value = "reply_user_id")
	@ApiModelProperty(value = "回复人id")
	private Long replyUserId;
	/**
	 * 父id
	 */
	
	@TableField(value = "parent_id")
	@ApiModelProperty(value = "父id")
	private Long parentId;
	/**
	 * 回复评论id
	 */

	@TableField(value = "reply_id")
	@ApiModelProperty(value = "回复评论id")
	private Long replyId;

	/**
	 * ip
	 */

	@TableField(value = "ip")
	@ApiModelProperty(value = "ip")
	private String ip;
	/**
	 * ip地址
	 */
	
	@TableField(value = "ip_address")
	@ApiModelProperty(value = "ip地址")
	private String ipAddress;
	/**
	 * 操作系统
	 */
	
	@TableField(value = "os")
	@ApiModelProperty(value = "操作系统")
	private String os;
	/**
	 * 状态:0未审核,1审核通过,2驳回
	 */
	@TableField(value = "status")
	@ApiModelProperty(value = "状态:0未审核,1审核通过,2驳回")
	private Integer status;
	/**
	 * 创建时间
	 */
	
	@TableField(value = "create_time", fill = FieldFill.INSERT)
	@ApiModelProperty(value = "创建时间")
	private LocalDateTime createTime;
	/**
	 * 修改时间
	 */
	
	@TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
	@ApiModelProperty(value = "修改时间")
	private LocalDateTime updateTime;
}
