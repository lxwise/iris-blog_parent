package com.iris.blog.controller.system;

import java.util.List;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaMode;
import com.iris.blog.common.annotation.OperateLog;
import com.iris.blog.domain.search.SearchRoleDTO;
import org.springframework.beans.factory.annotation.Autowired;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.iris.blog.dao.entity.RoleEntity;
import com.iris.blog.domain.dto.RoleDTO;
import com.iris.blog.service.RoleService;
import com.iris.blog.common.R;
import com.iris.blog.common.PageReq;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author lstar
 * @date: 2024-04
 * @description: 系统管理-角色表 
 */
@Api(tags = "系统管理-角色管理 ")
@Validated
@RestController
@RequestMapping("/system/role")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @ApiOperation(value = "角色列表", httpMethod = "POST", response = R.class, notes = "角色列表")
    @SaCheckPermission("system:role:query")
    @PostMapping("/list")
    public R selectRoleList(@RequestBody @Valid  PageReq<SearchRoleDTO> req){

        return roleService.selectRoleList(req);
    }

    @ApiOperation(value = "角色信息", httpMethod = "GET", response = R.class, notes = "角色信息")
    @SaCheckPermission("system:role:query")
    @GetMapping("/info")
    public R selectRoleById( Long id){

        return roleService.selectRoleById(id);
    }

    @ApiOperation(value = "保存角色", httpMethod = "POST", response = R.class, notes = "保存角色")
    @SaCheckPermission("system:role:save")
    @OperateLog(value = "保存角色")
    @PostMapping("/save")
    public R saveRole(@RequestBody @Valid  RoleDTO role){

        return roleService.saveRole(role);
    }

    @ApiOperation(value = "修改角色", httpMethod = "POST", response = R.class, notes = "修改角色")
    @SaCheckPermission("system:role:update")
    @OperateLog(value = "修改角色")
    @PostMapping("/update")
    public R updateRole(@RequestBody @Valid  RoleDTO role){

        return roleService.updateRole(role);
    }


    @ApiOperation(value = "删除角色", httpMethod = "POST", response = R.class, notes = "删除角色")
    @SaCheckPermission("system:role:delete")
    @OperateLog(value = "删除角色")
    @PostMapping("/delete")
    public R removeRoleByIds(@RequestBody @NotEmpty List<Long> ids){

        return roleService.removeRoleByIds(ids);
    }
    @GetMapping( "/get/current-user-role-menu")
    @SaCheckLogin
    @ApiOperation(value = "获取当前登录用户所拥有的权限", httpMethod = "GET", response = R.class, notes = "获取当前登录用户所拥有的权限")
    public R getCurrentUserRoleMenu() {
        return roleService.getCurrentUserRoleMenu();
    }

    @GetMapping("/get-all-menu-roleId")
    @SaCheckLogin
    @SaCheckPermission(value = {"system:role:query", "system:menu:query"}, mode = SaMode.OR)
    @ApiOperation(value = "获取该角色所有的权限", httpMethod = "GET", response = R.class, notes = "获取该角色所有的权限")
    public R getAllMenuRoleId(Long roleId) {
        return roleService.getAllMenuRoleId(roleId);
    }

    @ApiOperation(value = "角色下拉列表", httpMethod = "GET", response = R.class, notes = "角色下拉列表")
    @SaCheckLogin
    @GetMapping("/drop-list")
    public R roleDropList(){
        return roleService.roleDropList();
    }

}
