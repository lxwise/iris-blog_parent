package com.iris.blog.controller.system;

import java.util.List;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import com.iris.blog.common.annotation.OperateLog;
import com.iris.blog.domain.dto.UserUpdatePasswordDTO;
import com.iris.blog.domain.search.SearchUserDTO;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import javax.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.iris.blog.dao.entity.UserEntity;
import com.iris.blog.domain.dto.UserDTO;
import com.iris.blog.service.UserService;
import com.iris.blog.common.R;
import com.iris.blog.common.PageReq;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author lstar
 * @date: 2024-04
 * @description: 系统管理-用户表
 */
@Api(tags = "系统管理-用户管理")
@Validated
@RestController
@RequestMapping("/system/user")
public class UserController {
    @Autowired
    private UserService userService;

    @ApiOperation(value = "用户列表", httpMethod = "POST", response = R.class, notes = "用户列表")
    @SaCheckPermission("system:user:query")
    @PostMapping("/list")
    public R selectUserList(@RequestBody @Valid  PageReq<SearchUserDTO> req){

        return userService.selectUserList(req);
    }

    @ApiOperation(value = "用户信息", httpMethod = "GET", response = R.class, notes = "用户信息")
    @SaCheckPermission("system:user:query")
    @GetMapping("/info")
    public R selectUserById( Long id){

        return userService.selectUserById(id);
    }

    @ApiOperation(value = "保存用户", httpMethod = "POST", response = R.class, notes = "保存用户")
    @SaCheckPermission("system:user:save")
    @PostMapping("/save")
    @OperateLog(value = "新增用户")
    public R saveUser(@RequestBody @Valid  UserDTO user){

        return userService.saveUser(user);
    }

    @ApiOperation(value = "修改用户", httpMethod = "POST", response = R.class, notes = "修改用户")
    @SaCheckPermission("system:user:update")
    @OperateLog(value = "修改用户")
    @PostMapping("/update")
    public R updateUser(@RequestBody @Valid  UserDTO user){

        return userService.updateUser(user);
    }


    @ApiOperation(value = "删除用户", httpMethod = "POST", response = R.class, notes = "删除用户")
    @SaCheckPermission("system:user:delete")
    @OperateLog(value = "删除用户")
    @PostMapping("/delete")
    public R removeUserByIds(@RequestBody List<Long> ids){

        return userService.removeUserByIds(ids);
    }

    @ApiOperation(value = "启用/禁用", httpMethod = "GET", response = R.class, notes = "启用/禁用")
    @SaCheckPermission("system:user:update")
    @OperateLog(value = "启用/禁用用户")
    @GetMapping("/update/status")
    public R updateStatus( Long id){

        return userService.updateStatus(id);
    }

    @PostMapping("/update/password")
    @ApiOperation(value = "重置用户密码", httpMethod = "POST", response = R.class, notes = "重置用户密码")
    @SaCheckPermission("system:user:update-password")
    @OperateLog(value = "重置用户密码")
    public R updateUserPassword(@Valid @RequestBody UserUpdatePasswordDTO dto) {
        return userService.updateUserPassword(dto);
    }
}
