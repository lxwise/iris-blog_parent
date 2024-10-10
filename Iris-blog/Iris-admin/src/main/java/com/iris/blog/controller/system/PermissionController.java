package com.iris.blog.controller.system;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaMode;
import com.iris.blog.common.R;
import com.iris.blog.common.annotation.OperateLog;
import com.iris.blog.domain.dto.PermissionRoleMenuDTO;
import com.iris.blog.domain.dto.PermissionUserRoleDTO;
import com.iris.blog.domain.vo.AuthPermissionInfoVO;
import com.iris.blog.service.PermissionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.*;

import static java.util.Collections.singleton;

/**
 * @author lstar
 * @date: 2024-04
 * @description: 系统管理-权限Controller
 */
@Api(tags = "管理后台 - 权限分配")
@Validated
@RestController
@RequestMapping("/system/permission")
public class PermissionController {

    @Resource
    private PermissionService permissionService;

    @GetMapping("/list-role-menus")
    @SaCheckPermission(value = {"system:role:query", "system:menu:query"}, mode = SaMode.OR)
    @ApiOperation(value = "获得角色拥有的菜单权限", httpMethod = "GET", response = R.class, notes = "获得角色拥有的菜单权限")
    public R<Set<Long>> getRoleMenuList(@NotNull Long roleId) {
        return R.ok(permissionService.getRoleMenuListByRoleId(singleton(roleId)));
    }

    @PostMapping("/assign-role-menu")
    @SaCheckPermission("system:permission:assign-role-menu")
    @OperateLog(value = "分配角色菜单")
    @ApiOperation(value = "分配角色菜单权限", httpMethod = "POST", response = R.class, notes = "分配角色菜单权限")
    public R<Boolean> assignRoleMenu(@Valid @RequestBody PermissionRoleMenuDTO dto) {

        permissionService.assignRoleMenu(dto.getRoleId(), dto.getMenuIds());
        return R.ok();
    }


    @GetMapping("/list-user-roles")
    @SaCheckPermission(value = {"system:role:query", "system:menu:query"}, mode = SaMode.OR)
    @ApiOperation(value = "获得管理员拥有的角色列表", httpMethod = "POST", response = R.class, notes = "获得管理员拥有的角色列表")
    public R<Set<Long>> listAdminRoles(@RequestParam("userId") Long userId) {
        return R.ok(permissionService.getUserRoleIdListByUserId(userId));
    }

    @PostMapping("/assign-user-role")
    @SaCheckPermission("system:permission:assign-user-role")
    @OperateLog(value = "分配用户角色")
    @ApiOperation(value = "分配用户角色权限", httpMethod = "POST", response = R.class, notes = "分配用户角色权限")
    public R<Boolean> assignUserRole(@Validated @RequestBody PermissionUserRoleDTO dto) {
        permissionService.assignUserRole(dto.getUserId(), dto.getRoleIds());
        return R.ok();
    }

    @GetMapping("/get-permission-info")
    @SaCheckLogin
    @ApiOperation(value = "获取登录用户的权限信息", httpMethod = "GET", response = R.class, notes = "获取登录用户的权限信息")
    public R<AuthPermissionInfoVO> getPermissionInfo() {
        return  permissionService.getPermissionInfo();
    }

}
