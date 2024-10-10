package com.iris.blog.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author lstar
 * @date: 2024-04
 * @description: 文章表
 */
@Data
@ApiModel(value="文章表")
public class ArticleVO implements Serializable {
	private static final long serialVersionUID = 1L;


	@ApiModelProperty(value = "主键id")
	private Long id;

	@ApiModelProperty(value = "文章作者的ID")
	private Integer authorId;

	@ApiModelProperty(value = "作者昵称")
	private String authorName;

	@ApiModelProperty(value = "分类id")
	private Long categoryId;

	@ApiModelProperty(value = "分类名称")
	private String categoryName;

	@ApiModelProperty(value = "文章标签,多个用逗号隔开,最多3个")
	private String tags;

	@ApiModelProperty(value = "文章标签列表")
	private List<String> tagNameList;

	@ApiModelProperty(value = "标题")
	private String title;

	@ApiModelProperty(value = "封面地址")
	private String coverImage;

	@ApiModelProperty(value = "文章简介")
	private String intro;

	@ApiModelProperty(value = "文章内容")
	private String content;

	@ApiModelProperty(value = "阅读方式 0无需验证 1点赞阅读")
	private Boolean readType;

	@ApiModelProperty(value = "是否置顶 0否 1是")
	private Boolean isTop;

	@ApiModelProperty(value = "状态 0：草稿 1：发布 2:下架")
	private Integer status;

	@ApiModelProperty(value = "是否转载  0：转载 1:原创")
	private Boolean isForward;

	@ApiModelProperty(value = "转载地址")
	private String forwardUrl;

	@ApiModelProperty(value = "是否推荐 (0否 1是)")
	private Boolean isRecommend;

	@ApiModelProperty(value = "点赞数量")
	private Integer likes;

	@ApiModelProperty(value = "收藏数量")
	private Integer collection;

	@ApiModelProperty(value = "评论数量")
	private Integer comment;

	@ApiModelProperty(value = "阅读数量")
	private Integer views;

	@ApiModelProperty(value = "创建时间")
	private LocalDateTime createTime;

	@ApiModelProperty(value = "修改时间")
	private LocalDateTime updateTime;

}
