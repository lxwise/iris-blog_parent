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
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @author lstar
 * @date: 2024-04
 * @description: 文章表
 */
@Data
@TableName("t_article")
@ApiModel(value="文章表")
@Accessors(chain = true)
public class ArticleEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键id
	 */
	@TableId(value = "id")
	@ApiModelProperty(value = "主键id")
	private Long id;
	/**
	 * 文章作者的ID
	 */
	
	@TableField(value = "author_id")
	@ApiModelProperty(value = "文章作者的ID")
	private Long authorId;
	/**
	 * 作者昵称
	 */
	
	@TableField(value = "author_name")
	@ApiModelProperty(value = "作者昵称")
	private String authorName;
	/**
	 * 分类id
	 */
	
	@TableField(value = "category_id")
	@ApiModelProperty(value = "分类id")
	private Long categoryId;
	/**
	 * 分类名称
	 */
	
	@TableField(value = "category_name")
	@ApiModelProperty(value = "分类名称")
	private String categoryName;
	/**
	 * 文章标签,多个用逗号隔开,最多3个
	 */

	@TableField(value = "tags")
	@ApiModelProperty(value = "文章标签,多个用逗号隔开,最多3个")
	private String tags;
	/**
	 * 标题
	 */
	
	@TableField(value = "title")
	@ApiModelProperty(value = "标题")
	private String title;
	/**
	 * 封面地址
	 */
	
	@TableField(value = "cover_image")
	@ApiModelProperty(value = "封面地址")
	private String coverImage;
	/**
	 * 文章简介
	 */
	
	@TableField(value = "intro")
	@ApiModelProperty(value = "文章简介")
	private String intro;
	/**
	 * 文章内容
	 */
	
	@TableField(value = "content")
	@ApiModelProperty(value = "文章内容")
	private String content;
	/**
	 * 阅读方式 0无需验证 1点赞阅读
	 */
	
	@TableField(value = "read_type")
	@ApiModelProperty(value = "阅读方式 0无需验证 1点赞阅读")
	private Boolean readType;
	/**
	 * 是否置顶 0否 1是
	 */
	
	@TableField(value = "is_top")
	@ApiModelProperty(value = "是否置顶 0否 1是")
	private Boolean isTop;
	/**
	 * 是否下架 0：下架 1：发布
	 */
	
	@TableField(value = "status")
	@ApiModelProperty(value = "状态 0：草稿 1：发布 2:下架")
	private Integer status;
	/**
	 * 是否转载  0：转载 1:原创
	 */
	
	@TableField(value = "is_forward")
	@ApiModelProperty(value = "是否转载  0：转载 1:原创")
	private Boolean isForward;
	/**
	 * 转载地址
	 */
	
	@TableField(value = "forward_url")
	@ApiModelProperty(value = "转载地址")
	private String forwardUrl;

	/**
	 * 是否推荐 (0否 1是)
	 */
	@ApiModelProperty(value = "是否推荐 (0否 1是)")
	@TableField(value = "is_recommend")
	private Boolean isRecommend;
	/**
	 * 点赞数量
	 */
	
	@TableField(value = "likes")
	@ApiModelProperty(value = "点赞数量")
	private Integer likes;
	/**
	 * 收藏数量
	 */
	
	@TableField(value = "collection")
	@ApiModelProperty(value = "收藏数量")
	private Integer collection;
	/**
	 * 评论数量
	 */
	
	@TableField(value = "comment")
	@ApiModelProperty(value = "评论数量")
	private Integer comment;
	/**
	 * 阅读数量
	 */
	
	@TableField(value = "views")
	@ApiModelProperty(value = "阅读数量")
	private Integer views;
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

	@ApiModelProperty(value = "文章详情图片,最多九个")
	@TableField(value = "image_details")
	private String imageDetails;
}
