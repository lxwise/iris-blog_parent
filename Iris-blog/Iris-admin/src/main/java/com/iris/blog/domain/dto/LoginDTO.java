package com.iris.blog.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author lstar
 * @date: 2024-04
 * @description: 登录DTO
 */
@Data
public class LoginDTO {

    @NotNull(message = "用户名不能为空")
    @ApiModelProperty(value = "用户名")
    @Size(min = 1, max = 32, message = "用户名长度在1-32之间")
    private String username;

    @NotNull(message = "密码不能为空")
    @ApiModelProperty(value = "密码")
    @Size(min = 1, max = 32, message = "用户名长度在1-32之间")
    private String password;

    @ApiModelProperty(value = "记住我")
    private Boolean rememberMe;
    // ========== 图片验证码相关 ==========

    @ApiModelProperty(value = "验证码，验证码开启时，需要传递", required = true,
            example = "PfcH6mgr8tpXuMWFjvW6YVaqrswIuwmWI5dsVZSg7sGpWtDCUbHuDEXl3cFB1+VvCC/rAkSwK8Fad52FSuncVg==")
    private String captchaCode;
}