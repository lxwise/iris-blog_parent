package com.iris.blog.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.Set;

/**
 * @author lstar
 * @create 2024-05
 * @description: 分配用户角色
 */
@Data
public class PermissionUserRoleDTO {

    @ApiModelProperty(value = "用户ID", required = true, example = "1")
    @NotNull(message = "用户ID不能为空")
    private Long userId;

    @ApiModelProperty(value = "角色ID列表", example = "1,3,5")
    private Set<Long> roleIds = Collections.emptySet();

}
