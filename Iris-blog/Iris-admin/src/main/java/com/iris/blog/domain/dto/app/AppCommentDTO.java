package com.iris.blog.domain.dto.app;

import com.iris.blog.common.annotation.EnumValid;
import com.iris.blog.common.validator.CommentProvider;
import com.iris.blog.common.validator.groups.ArticleTalkGroup;
import com.iris.blog.common.validator.groups.LinkGroup;
import com.iris.blog.common.validator.groups.ParentIdNotNullGroup;
import com.iris.blog.common.validator.groups.ParentIdNullGroup;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.group.GroupSequenceProvider;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;


/**
 * @author lstar
 * @date: 2024-04
 * @description: 添加评论DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@GroupSequenceProvider(value = CommentProvider.class)
@ApiModel(description = "添加评论DTO")
public class AppCommentDTO {

    /**
     * 类型id
     */
    @NotNull(message = "类型id不能为空", groups = {ArticleTalkGroup.class})
    @Null(message = "类型id必须为空", groups = {LinkGroup.class})
    @ApiModelProperty(value = "类型id", required = true)
    private Integer typeId;

    /**
     * 评论类型 (1文章 2友链 3说说)
     */
    @EnumValid(values = {1, 2, 3}, message = "评论类型只能为1、2、3")
    @NotNull(message = "评论类型不能为空")
    @ApiModelProperty(value = "评论类型 (1文章 2友链 3说说)", required = true)
    private Integer commentType;

    /**
     * 父评论id
     */
    @Null(groups = {ParentIdNullGroup.class})
    @NotNull(groups = {ParentIdNotNullGroup.class})
    @ApiModelProperty(value = "父评论id", required = true)
    private Long parentId;

    /**
     * 被回复评论id
     */
    @Null(message = "reply_id、to_uid必须都为空", groups = {ParentIdNullGroup.class})
    @NotNull(message = "回复评论id和回复用户id不能为空", groups = {ParentIdNotNullGroup.class})
    @ApiModelProperty(value = "被回复评论id", required = true)
    private Long replyId;

    /**
     * 被回复用户id
     */
    @Null(message = "reply_id、to_uid必须都为空", groups = {ParentIdNullGroup.class})
    @NotNull(message = "回复评论id和回复用户id不能为空", groups = {ParentIdNotNullGroup.class})
    @ApiModelProperty(value = "被回复用户id", required = true)
    private Long toUid;

    /**
     * 评论内容
     */
    @NotBlank(message = "评论内容不能为空")
    @ApiModelProperty(value = "评论内容", required = true)
    private String commentContent;

}