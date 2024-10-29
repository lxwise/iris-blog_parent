package com.iris.blog.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author lstar
 * @date: 2024-04
 * @description: 评论类型枚举
 */
@Getter
@AllArgsConstructor
public enum CommentTypeEnum {

    /**
     * 文章评论
     */
    ARTICLE(1, "文章", "article/"),
    /**
     * 说说评论
     */
    TALK(2, "说说", "talk/"),

    /**
     * 友链评论
     */
    LINK(3, "友链", "friend"),
    ;
    /**
     * 状态
     */
    private final Integer type;

    /**
     * 描述
     */
    private final String desc;

    /**
     * 路径
     */
    private final String path;

    /**
     * 获取评论路径
     *
     * @param type 类型
     * @return {@link String}
     */
    public static String getCommentPath(Integer type) {
        for (CommentTypeEnum value : CommentTypeEnum.values()) {
            if (value.getType().equals(type)) {
                return value.getPath();
            }
        }
        return null;
    }

}