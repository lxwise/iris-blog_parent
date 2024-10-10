package com.iris.blog.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author lstar
 * @create 2024-05
 * @description: gitee登录配置属性
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "gitee")
public class GiteeConfigProperties {

    /**
     * 微博appId
     */
    private String appId;

    /**
     * 微博appSecret
     */
    private String appSecret;

    /**
     * 微博回调域名
     */
    private String redirectUrl;



}

