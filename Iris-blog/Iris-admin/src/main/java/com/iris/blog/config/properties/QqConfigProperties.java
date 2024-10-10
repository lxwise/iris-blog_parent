package com.iris.blog.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author lstar
 * @create 2024-05
 * @description: qq配置属性
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "qq")
public class QqConfigProperties {

    /**
     * QQ appId
     */
    private String appId;
    /**
     * QQ appkey
     */
    private String appSecret;
    /**
     * 回调地址
     */
    private String redirectUrl;

}
