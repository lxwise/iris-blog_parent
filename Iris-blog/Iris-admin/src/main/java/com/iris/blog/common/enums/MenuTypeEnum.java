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
public enum MenuTypeEnum {

    DIR(1), // 目录
    MENU(2), // 菜单
    BUTTON(3) // 按钮
    ;

    /**
     * 类型
     */
    private final Integer type;

}