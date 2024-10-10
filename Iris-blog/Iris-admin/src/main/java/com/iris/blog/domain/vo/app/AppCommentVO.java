package com.iris.blog.domain.vo.app;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author lstar
 * @date: 2024-04
 * @description: 评论AppCommentVO
 */
@Data
@ApiModel(description = "评论AppCommentVO")
public class AppCommentVO {

    /**
     * 评论id
     */
    @ApiModelProperty(value = "评论id")
    private Integer id;
    /**
     * 评论用户id
     */
    @ApiModelProperty(value = "评论用户id")
    private Integer fromUid;

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
     * 回复列表
     */
    @ApiModelProperty(value = "回复列表")
    private List<AppReplyCommentVO> replyVOList;

    /**
     * 评论时间
     */
    @ApiModelProperty(value = "评论时间")
    private LocalDateTime createTime;
}