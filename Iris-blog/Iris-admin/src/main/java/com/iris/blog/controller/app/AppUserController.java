package com.iris.blog.controller.app;

import cn.dev33.satoken.annotation.SaCheckLogin;
import com.iris.blog.common.R;
import com.iris.blog.common.annotation.RLimit;
import com.iris.blog.domain.dto.app.AppRegisterDTO;
import com.iris.blog.domain.dto.app.AppUpdateEmailDTO;
import com.iris.blog.domain.dto.app.AppUserDTO;
import com.iris.blog.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * @author lstar
 * @create 2024-05
 * @description: 用户接口-API
 */
@RequestMapping("/v1/user")
@RestController
@Api(tags = "用户接口-API")
public class AppUserController {
    @Resource
    private UserService userService;
    @SaCheckLogin
    @ApiOperation(value = "获取登录用户信息", httpMethod = "GET", response = R.class, notes = "获取登录用户信息")
    @GetMapping("/info")
    public R getUserInfo() {
        return userService.getUserInfo();
    }

    @ApiOperation(value = "修改用户邮箱", httpMethod = "POST", response = R.class, notes = "修改用户邮箱")
    @SaCheckLogin
    @PostMapping("/user/email")
    public R updateUserEmail(@Validated @RequestBody AppUpdateEmailDTO dto) {
        userService.updateUserEmail(dto);
        return R.ok();
    }

    @ApiOperation(value = "修改用户头像", httpMethod = "POST", response = R.class, notes = "修改用户头像")
    @SaCheckLogin
    @RLimit(count = 1, time = 86400,msg = "修改头像频繁，请一天后重试~")
    @PostMapping("/user/avatar")
    public R updateUserAvatar(@RequestParam(value = "file") MultipartFile file) {
        return userService.updateUserAvatar(file);
    }

    @ApiOperation(value = "修改用户信息", httpMethod = "POST", response = R.class, notes = "修改用户信息")
    @SaCheckLogin
    @PostMapping("/user/info")
    public R updateUserInfo(@Validated @RequestBody AppUserDTO dto) {
        userService.updateUserInfo(dto);
        return R.ok();
    }

    @ApiOperation(value = "修改用户密码", httpMethod = "POST", response = R.class, notes = "修改用户密码")
    @SaCheckLogin
    @PostMapping("/user/password")
    public R updatePassword(@Validated @RequestBody AppRegisterDTO dto) {
        userService.updatePassword(dto);
        return R.ok();
    }
}

