package com.iris.blog.domain.vo.app;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author lstar
 * @create 2024-08
 * @description: 最新评论VO
 */
@Data
@ApiModel(description = "最新评论VO")
public class AppRecentCommentLatestVO {

    @ApiModelProperty(value = "评论id")
    private Integer id;

    @ApiModelProperty(value = "用户昵称")
    private String nickname;

    @ApiModelProperty(value = "用户头像")
    private String avatar;

    @ApiModelProperty(value = "评论内容")
    private String commentContent;

    @ApiModelProperty(value = "评论时间")
    private LocalDateTime createTime;
}