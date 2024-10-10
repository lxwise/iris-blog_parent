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
 * @date: 2024-06
 * @description: 网站配置
 */
@Data
@TableName("t_site_config")
@ApiModel(value="网站配置")
public class SiteConfigEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@TableId(value = "id")
	@ApiModelProperty(value = "主键")
	private Integer id;
	/**
	 * 用户头像
	 */
	
	@TableField(value = "user_avatar")
	@ApiModelProperty(value = "用户头像")
	private String userAvatar;
	/**
	 * 游客头像
	 */
	
	@TableField(value = "tourist_avatar")
	@ApiModelProperty(value = "游客头像")
	private String touristAvatar;
	/**
	 * 网站名称
	 */
	
	@TableField(value = "site_name")
	@ApiModelProperty(value = "网站名称")
	private String siteName;
	/**
	 * 网站地址
	 */
	
	@TableField(value = "site_address")
	@ApiModelProperty(value = "网站地址")
	private String siteAddress;
	/**
	 * 网站简介
	 */
	
	@TableField(value = "site_intro")
	@ApiModelProperty(value = "网站简介")
	private String siteIntro;
	/**
	 * 网站公告
	 */
	
	@TableField(value = "site_notice")
	@ApiModelProperty(value = "网站公告")
	private String siteNotice;
	/**
	 * 建站日期
	 */
	
	@TableField(value = "create_site_time")
	@ApiModelProperty(value = "建站日期")
	private String createSiteTime;
	/**
	 * 备案号
	 */
	
	@TableField(value = "record_number")
	@ApiModelProperty(value = "备案号")
	private String recordNumber;
	/**
	 * 作者头像
	 */
	
	@TableField(value = "author_avatar")
	@ApiModelProperty(value = "作者头像")
	private String authorAvatar;
	/**
	 * 网站作者
	 */
	
	@TableField(value = "site_author")
	@ApiModelProperty(value = "网站作者")
	private String siteAuthor;
	/**
	 * 文章默认封面
	 */
	
	@TableField(value = "article_cover")
	@ApiModelProperty(value = "文章默认封面")
	private String articleCover;
	/**
	 * 关于我
	 */
	
	@TableField(value = "about_me")
	@ApiModelProperty(value = "关于我")
	private String aboutMe;

	/**
	 * Github
	 */

	@TableField(value = "github")
	@ApiModelProperty(value = "Github")
	private String github;
	/**
	 * 邮箱
	 */
	
	@TableField(value = "email")
	@ApiModelProperty(value = "email")
	private String email;
	/**
	 * Gitee
	 */
	
	@TableField(value = "gitee")
	@ApiModelProperty(value = "Gitee")
	private String gitee;
	/**
	 * QQ群
	 */
	
	@TableField(value = "qq_group")
	@ApiModelProperty(value = "QQ群")
	private String qqGroup;
	/**
	 * QQ
	 */
	
	@TableField(value = "qq")
	@ApiModelProperty(value = "QQ")
	private String qq;
	/**
	 * 是否评论审核 (0否 1是)
	 */
	
	@TableField(value = "comment_check")
	@ApiModelProperty(value = "是否评论审核 (0否 1是)")
	private Integer commentCheck;
	/**
	 * 是否留言审核 (0否 1是)
	 */
	
	@TableField(value = "message_check")
	@ApiModelProperty(value = "是否留言审核 (0否 1是)")
	private Integer messageCheck;
	/**
	 * 是否开启打赏 (0否 1是)
	 */
	
	@TableField(value = "is_reward")
	@ApiModelProperty(value = "是否开启打赏 (0否 1是)")
	private Integer isReward;
	/**
	 * 微信二维码
	 */
	
	@TableField(value = "wechat_code")
	@ApiModelProperty(value = "微信二维码")
	private String wechatCode;
	/**
	 * 支付宝二维码
	 */
	
	@TableField(value = "alipay_code")
	@ApiModelProperty(value = "支付宝二维码")
	private String alipayCode;
	/**
	 * 是否邮箱通知 (0否 1是)
	 */
	
	@TableField(value = "email_notice")
	@ApiModelProperty(value = "是否邮箱通知 (0否 1是)")
	private Integer emailNotice;
	/**
	 * 社交列表
	 */
	
	@TableField(value = "social_list")
	@ApiModelProperty(value = "社交列表")
	private String socialList;
	/**
	 * 登录方式
	 */
	
	@TableField(value = "login_list")
	@ApiModelProperty(value = "登录方式")
	private String loginList;
	/**
	 * 是否开启音乐播放器 (0否 1是)
	 */
	
	@TableField(value = "is_music")
	@ApiModelProperty(value = "是否开启音乐播放器 (0否 1是)")
	private Integer isMusic;
	/**
	 * 网易云歌单id
	 */
	
	@TableField(value = "music_id")
	@ApiModelProperty(value = "网易云歌单id")
	private String musicId;
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
