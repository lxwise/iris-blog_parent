package com.iris.blog.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

/**
 * @author lstar
 * @create 2024-05
 * @ value:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthPermissionInfoVO {

    @ApiModelProperty(value = "用户信息" )
    private UserVO user;

    @ApiModelProperty( value = "角色标识数组" )
    private Set<String> roles;

    @ApiModelProperty( value = "操作权限数组" )
    private Set<String> permissions;

    @ApiModelProperty( value = "菜单树" )
    private List<MenuVO> menus;

    @ApiModel( value = "用户信息 VO")
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class UserVO {

        @ApiModelProperty( value = "用户编号" )
        private Long id;

        @ApiModelProperty( value = "用户昵称" )
        private String nickname;

        @ApiModelProperty( value = "用户头像" )
        private String avatar;

    }

    @ApiModel( value = "管理后台 - 登录用户的菜单信息")
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class MenuVO {

        @ApiModelProperty(value = "主键ID")
        private Long id;

        @ApiModelProperty(value = "菜单名称")
        private String name;

        @ApiModelProperty(value = "父ID")
        private Long parentId;

        @ApiModelProperty(value = "路由地址")
        private String path;

        @ApiModelProperty(value = "菜单图标")
        private String icon;

        @ApiModelProperty(value = "组件路径")
        private String component;

        @ApiModelProperty(value = "组件名")
        private String componentName;

        @ApiModelProperty(value = "是否可见")
        private Integer visible;

        @ApiModelProperty(value = "是否缓存")
        private Integer keepAlive;

        @ApiModelProperty(value = "是否总是显示")
        private Integer alwaysShow;

        /**
         * 子路由
         */
        private List<MenuVO> children;

    }
}
