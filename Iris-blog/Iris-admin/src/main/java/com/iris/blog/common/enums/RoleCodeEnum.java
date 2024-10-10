package com.iris.blog.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

/**
 * 角色标识枚举
 */
@Getter
@AllArgsConstructor
public enum RoleCodeEnum {

    SUPER_ADMIN("super_admin", "超级管理员",1L,1L),
    COMMON("common", "普通角色",2L,2L),
    TEST_USER("test", "测试角色",3L,3L),
    ;

    /**
     * 角色编码
     */
    private final String code;
    /**
     * 名字
     */
    private final String name;
    /**
     * 角色id
     */
    private final Long roleId;
    /**
     * 用户id
     */
    private final Long userId;

    public static boolean isSuperAdmin(String code) {
        return Objects.equals(code, SUPER_ADMIN.getCode());
    }

}