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
 * @description: 友情链接表
 */
@Data
@TableName("t_friend_link")
@ApiModel(value="友情链接表")
public class FriendLinkEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键ID
	 */
	@TableId(value = "id")
	@ApiModelProperty(value = "主键ID")
	private Long id;
	/**
	 * 网站名称
	 */
	
	@TableField(value = "name")
	@ApiModelProperty(value = "网站名称")
	private String name;
	/**
	 * 网站地址
	 */
	
	@TableField(value = "url")
	@ApiModelProperty(value = "网站地址")
	private String url;
	/**
	 * 网站logo
	 */
	
	@TableField(value = "image")
	@ApiModelProperty(value = "网站logo")
	private String image;
	/**
	 * 网站描述
	 */
	
	@TableField(value = "info")
	@ApiModelProperty(value = "网站描述")
	private String info;
	/**
	 * 联系邮箱
	 */
	
	@TableField(value = "email")
	@ApiModelProperty(value = "联系邮箱")
	private String email;
	/**
	 * 是否展示:0申请,1展示,2不展示
	 */
	
	@TableField(value = "status")
	@ApiModelProperty(value = "是否展示:0申请,1展示,2不展示")
	private Integer status;
	/**
	 * 排序
	 */
	
	@TableField(value = "sort")
	@ApiModelProperty(value = "排序")
	private Integer sort;
	/**
	 * 创建时间
	 */
	
	@TableField(value = "create_time", fill = FieldFill.INSERT)
	@ApiModelProperty(value = "创建时间")
	private LocalDateTime createTime;
	/**
	 * 更新时间
	 */
	
	@TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
	@ApiModelProperty(value = "更新时间")
	private LocalDateTime updateTime;
}
