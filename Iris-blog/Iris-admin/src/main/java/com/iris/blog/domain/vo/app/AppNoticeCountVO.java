package com.iris.blog.domain.vo.app;

import lombok.Data;

/**
 * @author lstar
 * @create 2024-09
 * @description: 未读消息数量
 */
@Data
public class AppNoticeCountVO {

    /**
     * 系统通知数量
     */
    private Integer systemNoticeCount;

    /**
     * 评论通知数量
     */
    private Integer commentNoticeCount;

    /**
     * 点赞通知数量
     */
    private Integer likeNoticeCount;
}
