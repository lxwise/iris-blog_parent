package com.iris.blog.domain.vo.app;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author lstar
 * @date: 2024-04
 * @description: 评论回复AppReplyCommentVO
 */
@Data
@ApiModel(description = "评论回复AppReplyCommentVO")
public class AppReplyCommentVO {

    /**
     * 评论id
     */
    @ApiModelProperty(value = "评论id")
    private Integer id;

    /**
     * 父级评论id
     */
    @ApiModelProperty(value = "父级评论id")
    private Integer parentId;

    /**
     * 评论用户id
     */
    @ApiModelProperty(value = "评论用户id")
    private Integer fromUid;
    /**
     * 被回复人id
     */
    @ApiModelProperty(value = "被回复人id")
    private Long toUid;

    /**
     * 回复人
     */
    @ApiModelProperty(value = "回复人")
    private String toNickname;

    /**
     * 昵称
     */
    @ApiModelProperty(value = "昵称")
    private String fromNickname;

    /**
     * 头像
     */
    @ApiModelProperty(value = "头像")
    private String avatar;

    /**
     * 评论内容
     */
    @ApiModelProperty(value = "评论内容")
    private String commentContent;

    /**
     * 点赞数
     */
    @ApiModelProperty(value = "点赞数")
    private Integer likeCount;

    /**
     * 回复量
     */
    @ApiModelProperty(value = "回复量")
    private Integer replyCount;

    /**
     * 评论时间
     */
    @ApiModelProperty(value = "评论时间")
    private LocalDateTime createTime;
}