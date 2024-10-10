package com.iris.blog.domain.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@ApiModel(value="网站配置")
public class SiteConfigVO implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "主键")
	private Integer id;

	@ApiModelProperty(value = "用户头像")
	private String userAvatar;

	@ApiModelProperty(value = "游客头像")
	private String touristAvatar;

	@ApiModelProperty(value = "网站名称")
	private String siteName;

	@ApiModelProperty(value = "网站地址")
	private String siteAddress;

	@ApiModelProperty(value = "网站简介")
	private String siteIntro;

	@ApiModelProperty(value = "网站公告")
	private String siteNotice;

	@ApiModelProperty(value = "建站日期")
	private String createSiteTime;

	@ApiModelProperty(value = "备案号")
	private String recordNumber;

	@ApiModelProperty(value = "作者头像")
	private String authorAvatar;

	@ApiModelProperty(value = "网站作者")
	private String siteAuthor;

	@ApiModelProperty(value = "文章默认封面")
	private String articleCover;

	@ApiModelProperty(value = "关于我")
	private String aboutMe;

	@ApiModelProperty(value = "email")
	private String email;

	@ApiModelProperty(value = "Github")
	private String github;

	@ApiModelProperty(value = "Gitee")
	private String gitee;

	@ApiModelProperty(value = "QQ群")
	private String qqGroup;

	@ApiModelProperty(value = "QQ")
	private String qq;

	@ApiModelProperty(value = "是否评论审核 (0否 1是)")
	private Integer commentCheck;

	@ApiModelProperty(value = "是否留言审核 (0否 1是)")
	private Integer messageCheck;

	@ApiModelProperty(value = "是否开启打赏 (0否 1是)")
	private Integer isReward;

	@ApiModelProperty(value = "微信二维码")
	private String wechatCode;

	@ApiModelProperty(value = "支付宝二维码")
	private String alipayCode;

	@ApiModelProperty(value = "是否邮箱通知 (0否 1是)")
	private Integer emailNotice;

	@ApiModelProperty(value = "社交列表")
	private String socialList;

	@ApiModelProperty(value = "登录方式")
	private String loginList;

	@ApiModelProperty(value = "是否开启音乐播放器 (0否 1是)")
	private Integer isMusic;

	@ApiModelProperty(value = "网易云歌单id")
	private String musicId;

	@ApiModelProperty(value = "创建时间")
	@JsonIgnore
	private LocalDateTime createTime;

	@ApiModelProperty(value = "更新时间")
	@JsonIgnore
	private LocalDateTime updateTime;

}
