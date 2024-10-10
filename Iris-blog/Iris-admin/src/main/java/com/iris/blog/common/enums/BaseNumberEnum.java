package com.iris.blog.common.enums;

import lombok.Getter;

/**
 * @author lstar
 * @date: 2024-04
 * @description: 常用数字枚举
 */
@Getter
public enum BaseNumberEnum {

    ZERO(0, "0"),
    ONE(1, "1"),
    TWO(2, "2"),
    THREE(3, "3"),
    FOUR(4, "4"),
    FIVE(5, "5"),
    SIX(6, "6"),
    SEVEN(7, "7"),
    EIGHT(8, "8"),
    NINE(9, "9"),
    TEN(10, "10");

    private Integer code;
    private String msg;
    BaseNumberEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}