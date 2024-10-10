package com.iris.blog.dao.entity;

import com.baomidou.mybatisplus.annotation.*;
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
@TableName("t_feed_back")
@ApiModel(value="用户反馈表")
public class FeedBackEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键ID
	 */
	@TableId(value = "id",type = IdType.AUTO)
	@ApiModelProperty(value = "主键ID")
	private Long id;
	/**
	 * 用户id
	 */
	
	@TableField(value = "user_id")
	@ApiModelProperty(value = "用户id")
	private Long userId;
	/**
	 * 标题
	 */
	
	@TableField(value = "title")
	@ApiModelProperty(value = "标题")
	private String title;
	/**
	 * 详细内容
	 */
	
	@TableField(value = "content")
	@ApiModelProperty(value = "详细内容")
	private String content;
	/**
	 * 图片地址
	 */
	
	@TableField(value = "img_url")
	@ApiModelProperty(value = "图片地址")
	private String imgUrl;
	/**
	 * 反馈类型 0:需求 1：缺陷
	 */
	
	@TableField(value = "back_type")
	@ApiModelProperty(value = "反馈类型 0:需求 1：缺陷")
	private Boolean backType;
	/**
	 * 状态 0未解决 1解决
	 */
	
	@TableField(value = "status")
	@ApiModelProperty(value = "状态 0未解决 1解决")
	private Boolean status;
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
