package com.iris.blog.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum LoginTypeEnum {

    /**
     * 邮箱登录
     */
    EMAIL(1, "email"),
    /**
     * QQ登录
     */
    QQ(2, "qq"),
    /**
     * 微博登录
     */
    WEIBO(3, "weibo"),

    /**
     * 码云登录
     */
    GITEE(4, "gitee"),

    /**
     * github登录
     */
    GITHUB(5, "github");

    /**
     * 登录方式
     */
    private final Integer type;

    /**
     * 描述
     */
    private final String desc;


    public static Integer getType(String desc) {
        for (LoginTypeEnum value : LoginTypeEnum.values()) {
            if (value.getDesc().equals(desc)) {
                return value.getType();
            }
        }
        return null;
    }

}
