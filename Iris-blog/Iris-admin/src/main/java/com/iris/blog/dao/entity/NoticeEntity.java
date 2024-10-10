package com.iris.blog.dao.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
 * @description: 系统通知
 */
@Data
@TableName("t_notice")
@ApiModel(value="系统通知")
public class NoticeEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键id
	 */
	@TableId(value = "id")
	@ApiModelProperty(value = "主键id")
	private Long id;
	/**
	 * 发送用户id
	 */
	
	@TableField(value = "to_user_id")
	@ApiModelProperty(value = "发送用户id")
	private Long toUserId;

	/**
	 * 发送用户昵称
	 */
	@TableField(value = "to_user_nickname")
	@ApiModelProperty(value = "发送用户昵称")
	private String toUserNickname;

	/**
	 * 发送用户头像
	 */
	
	@TableField(value = "to_user_avatar")
	@ApiModelProperty(value = "发送用户头像")
	private String toUserAvatar;
	/**
	 * 接收用户id
	 */
	
	@TableField(value = "from_user_id")
	@ApiModelProperty(value = "接收用户id")
	private Long fromUserId;
	/**
	 * 通知内容
	 */
	
	@TableField(value = "content")
	@ApiModelProperty(value = "通知内容")
	private String content;
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
	 * 省
	 */
	
	@TableField(value = "province")
	@ApiModelProperty(value = "省")
	private String province;
	/**
	 * 市
	 */
	
	@TableField(value = "city")
	@ApiModelProperty(value = "市")
	private String city;
	/**
	 * 区
	 */
	
	@TableField(value = "region")
	@ApiModelProperty(value = "区")
	private String region;
	/**
	 * 纬度
	 */
	
	@TableField(value = "latitude")
	@ApiModelProperty(value = "纬度")
	private String latitude;
	/**
	 * 经度
	 */
	
	@TableField(value = "longitude")
	@ApiModelProperty(value = "经度")
	private String longitude;
	/**
	 * 是否已读:0未读,1已读
	 */
	
	@TableField(value = "status")
	@ApiModelProperty(value = "是否已读:0未读,1已读")
	private Integer status;
	/**
	 * 通知类型:1系统通知2评论,3点赞
	 */
	
	@TableField(value = "notice_type")
	@ApiModelProperty(value = "通知类型:1系统通知2评论,3点赞")
	private Integer noticeType;

	/**
	 * 通知类型路径:系统通知notice,talk/article/friend
	 */

	@TableField(value = "notice_type_path")
	@ApiModelProperty(value = "通知路径:notice/talk/article/friend")
	private String noticeTypePath;
	/**
	 * 通知类型id:系统通知null
	 */
	
	@TableField(value = "type_id")
	@ApiModelProperty(value = "通知类型id:系统通知null")
	private Long typeId;
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
