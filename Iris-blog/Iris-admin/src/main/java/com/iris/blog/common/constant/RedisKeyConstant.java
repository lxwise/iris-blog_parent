package com.iris.blog.common.constant;

/**
 * @author lstar
 * @create 2023-04
 * @description: redis key 常量
 */
public interface RedisKeyConstant {
    /**
     * 权限--用户所拥有角色
     */
    String USER_ROLE_KEY = "Authorization:login:role:";

    /**
     * 权限--用户角色所拥有菜单
     */

    String USER_ROLE_MENU_KEY = "Authorization:login:menu:";

    /**
     * 在线用户信息
     */
    String USER_ONLINE_INFO = "user:onlineInfo:";
    /**
     * 用户登录验证码
     */
    String USER_LOGIN_CAPTCHA = "user:login:captcha:";

    /**
     * 系统日志Key
     */
    String SYS_LOG_KEY = "sys:log:key";
    /**
     * 系统ip缓存Key
     */
    String SYS_IP_KEY = "sys:ip:key:";
    /**
     * 系统参数Key
     */
    String SYS_PARAMS_KEY = "sys:params:key";
    /**
     * 天气信息Key
     */
    String SYS_WEATHER_INFO_KEY = "sys:weather:info:key:";

    /**
     * 网站配置key
     */
     String SYS_SITE_SETTING = "sys:site:setting:key";
    /**
     * 网站配置key
     */
     String SYS_TAG_INFO_KEY = "sys:tag:info:key";


     /****************APP端相关********************/
    /**
     * APP用户登录验证码
     */
    String USER_LOGIN_CODE = "user:login:code:";
    /**
     * 访客
     */
    String UNIQUE_VISITOR = "unique_visitor";

    /**
     *博客浏览量
     */
    String BLOG_VIEW_COUNT = "blog:view:count";

    /**
     * 文章浏览量
     */
    String ARTICLE_VIEW_COUNT = "article:view:count";

    /**
     * 文章点赞量
     */
    String ARTICLE_LIKE_COUNT = "article:like:count";

    /**
     * 用户点赞文章
     */
    String USER_ARTICLE_LIKE = "user:article:like:";

    /**
     * 用户点赞评论
     */
    String USER_COMMENT_LIKE = "user:comment:like:";

    /**
     * 用户点赞说说
     */
    String USER_TALK_LIKE = "user:talk:like:";
    /**
     * 评论点赞量
     */
    String COMMENT_LIKE_COUNT = "comment_like_count";
    /**
     * 说说点赞量
     */
    String TALK_LIKE_COUNT = "talk_like_count";
}
