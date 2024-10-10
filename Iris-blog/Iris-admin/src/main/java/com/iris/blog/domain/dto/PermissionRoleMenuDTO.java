package com.iris.blog.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.Set;

/**
 * @author lstar
 * @create 2024-05
 * @description: 分配角色菜单
 */
@Data
public class PermissionRoleMenuDTO {
    @ApiModelProperty(value = "角色id", required = true, example = "1")
    @NotNull(message = "角色编号不能为空")
    private Long roleId;

    @ApiModelProperty(value = "菜单编号列表", example = "1,3,5")
    private Set<Long> menuIds = Collections.emptySet();
}
