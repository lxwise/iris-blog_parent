package com.iris.blog.config.satoken;

import lombok.*;

import java.time.LocalDateTime;

/**
 * @author lstar
 * @create 2023-04
 * @description: 在线用户
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class OnlineUserInfo {
    /**
     * 登录id
     */
    private Object loginId;
    /**
     * 电话
     */
    private String phone;
    /**
     * wx
     */
    private String wx;
    /**
     * qq
     */
    private String qq;
    /**
     * email
     */
    private String email;
    /**
     * 用户昵称
     */
    private String nickname;
    /**
     * 性别
     */
    private Integer sex;
    /**
     * 头像
     */
    private String avatar;
    /**
     * ip地址
     */
    private String ip;

    /**
     * 地址
     */
    private String address;

    /**
     * 省
     */
    private String province;

    /**
     * 市
     */
    private String city;

    /**
     * 区
     */
    private String region;

    /**
     * 登录时间
     */
    private LocalDateTime loginTime;
}

