package com.iris.blog.domain.config;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author lstar
 * @create 2024-09
 * @description: 邮件配置
 */
@Data
public class EmailConfig {

    @ApiModelProperty(value = "邮箱地址")
    private String emailHost;
    @ApiModelProperty(value = "邮箱发件人")
    private String emailUsername;
    @ApiModelProperty(value = "邮箱授权码")
    private String emailPassword;
    @ApiModelProperty(value = "邮箱端口")
    private int emailPort;
}
