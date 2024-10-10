package com.iris.blog.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author lstar
 * @create 2024-05
 * @description:
 */
@Data
public class UserUpdatePasswordDTO {


    @ApiModelProperty(value = "用户编号")
    @NotNull(message = "用户id不能为空")
    private Long id;

    @ApiModelProperty(value = "密码")
    @NotEmpty(message = "密码不能为空")
    @Length(min = 4, max = 16, message = "密码长度为 4-16 位")
    private String password;
}
