package com.iris.blog.controller.system;

import java.util.List;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaCheckRole;
import cn.dev33.satoken.annotation.SaMode;
import com.iris.blog.common.annotation.OperateLog;
import com.iris.blog.domain.search.SearchMenuDTO;
import com.iris.blog.domain.vo.MenuSimpleVO;
import javax.annotation.Resource;
import javax.validation.Valid;
import org.springframework.validation.annotation.Validated;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.*;
import com.iris.blog.domain.dto.MenuDTO;
import com.iris.blog.service.MenuService;
import com.iris.blog.common.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author lstar
 * @date: 2024-04
 * @description: 系统管理-菜单权限表
 */
@Api(tags = "系统管理-菜单权限管理 ")
@Validated
@RestController
@RequestMapping("/system/menu")
public class MenuController {

    @Resource
    private MenuService menuService;

    @ApiOperation(value = "菜单权限列表", httpMethod = "POST", response = R.class, notes = "菜单权限列表")
    @SaCheckPermission("system:menu:query")
    @PostMapping("/list")
    public R selectMenuList(@RequestBody SearchMenuDTO req){

        return menuService.selectMenuList(req);
    }

    @ApiOperation(value = "菜单权限信息", httpMethod = "GET", response = R.class, notes = "菜单权限信息")
    @SaCheckPermission("system:menu:query")
    @GetMapping("/info")
    public R selectMenuById(@NotNull Long id){

        return menuService.selectMenuById(id);
    }

    @ApiOperation(value = "保存菜单权限", httpMethod = "POST", response = R.class, notes = "保存菜单权限")
    @SaCheckPermission(value = "system:menu:save")
    @OperateLog(value = "保存菜单权限")
    @PostMapping("/save")
    public R saveMenu(@RequestBody @Valid  MenuDTO menu){

        return menuService.saveMenu(menu);
    }

    @ApiOperation(value = "修改菜单权限", httpMethod = "POST", response = R.class, notes = "修改菜单权限")
    @SaCheckPermission(value = "system:menu:update")
    @OperateLog(value = "修改菜单权限")
    @PostMapping("/update")
    public R updateMenu(@RequestBody @Valid  MenuDTO menu){

        return menuService.updateMenu(menu);
    }


    @ApiOperation(value = "删除菜单权限", httpMethod = "GET", response = R.class, notes = "删除菜单权限")
    @SaCheckPermission(value = "system:menu:delete")
    @OperateLog(value = "删除菜单权限")
    @GetMapping("/delete")
    public R removeMenuByIds(@NotNull Long id){

        return menuService.removeMenuByIds(id);
    }

    @SaCheckLogin
    @GetMapping("/select/list")
    @ApiOperation(value = "获取菜单下拉列表", httpMethod = "POST", response = R.class, notes = "获取菜单下拉列表")
    public R<List<MenuSimpleVO>> getSelectMenuList() {
        return menuService.getSelectMenuList();
    }

}
