package com.iris.blog.controller.system;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.StpUtil;

import com.iris.blog.common.R;
import com.iris.blog.common.annotation.OperateLog;
import com.iris.blog.domain.dto.CaptchaDTO;
import com.iris.blog.domain.dto.LoginDTO;
import com.iris.blog.service.LoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @author lstar
 * @date: 2024-04
 * @description: 登录-接口
 */
@Api(tags = "登录-接口")
@Validated
@RestController
@RequestMapping("/system/login")
public class LoginController {

    @Resource
    private LoginService loginService;


    @ApiOperation(value = "登录", httpMethod = "POST", response = R.class, notes = "登录")
    @PostMapping("login")
    public R login(@Valid @RequestBody LoginDTO dto) {
        return loginService.login(dto);
    }

    @ApiOperation(value = "退出登录", httpMethod = "GET", response = R.class, notes = "退出登录")
    @SaCheckLogin
    @GetMapping("logout")
    public R logout() {
        StpUtil.logout();
        return R.ok("退出成功");
    }
}
