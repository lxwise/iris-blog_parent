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
 * @date: 2024-08
 * @description: 说说
 */
@Data
@TableName("t_talk")
@ApiModel(value="说说")
public class TalkEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 说说id
	 */
	@TableId(value = "id")
	@ApiModelProperty(value = "说说id")
	private Long id;
	/**
	 * 用户id
	 */
	
	@TableField(value = "user_id")
	@ApiModelProperty(value = "用户id")
	private Long userId;
	/**
	 * 说说内容
	 */
	
	@TableField(value = "talk_content")
	@ApiModelProperty(value = "说说内容")
	private String talkContent;
	/**
	 * 是否置顶 (0否 1是)
	 */
	
	@TableField(value = "is_top")
	@ApiModelProperty(value = "是否置顶 (0否 1是)")
	private Integer isTop;
	/**
	 * 状态 (1公开  2私密)
	 */
	
	@TableField(value = "status")
	@ApiModelProperty(value = "状态 (1公开  2私密)")
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
