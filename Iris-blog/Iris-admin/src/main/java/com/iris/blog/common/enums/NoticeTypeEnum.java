package com.iris.blog.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author lstar
 * @date: 2024-04
 * @description: 菜单类型枚举类
 */
@Getter
@AllArgsConstructor
public enum NoticeTypeEnum {

    NOTICE("notice"),
    TALK("talk"),
    ARTICLE("article") ,
    FRIEND("friend")
    ;

    /**
     * 类型
     */
    private final String path;

}