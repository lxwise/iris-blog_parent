package com.iris.blog.domain.vo.app;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author lstar
 * @create 2024-08
 * @description: 评论回复数VO
 */
@Data
@ApiModel(description = "评论回复数VO")
public class AppReplyCommentCountVO {

    /**
     * 评论id
     */
    @ApiModelProperty(value = "评论id")
    private Integer commentId;

    /**
     * 回复数
     */
    @ApiModelProperty(value = "回复数")
    private Integer replyCount;

}