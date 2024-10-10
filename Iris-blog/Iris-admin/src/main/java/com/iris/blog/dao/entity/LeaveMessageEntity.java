package com.iris.blog.dao.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
@TableName("t_leave_message")
@ApiModel(value="用户留言表")
public class LeaveMessageEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键ID
	 */
	@TableId(value = "id")
	@ApiModelProperty(value = "主键ID")
	private Long id;
	/**
	 * 内容
	 */
	
	@TableField(value = "content")
	@ApiModelProperty(value = "内容")
	private String content;
	/**
	 * 昵称
	 */
	@TableField(value = "nickname")
	@ApiModelProperty(value = "昵称")
	private String nickname;
	/**
	 * 头像
	 */
	
	@TableField(value = "avatar")
	@ApiModelProperty(value = "头像")
	private String avatar;
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
