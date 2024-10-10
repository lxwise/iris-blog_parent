package com.iris.blog.controller.system;

import java.util.List;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.iris.blog.common.annotation.OperateLog;
import org.springframework.beans.factory.annotation.Autowired;
import javax.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.iris.blog.dao.entity.UserInfoEntity;
import com.iris.blog.domain.dto.UserInfoDTO;
import com.iris.blog.service.UserInfoService;
import com.iris.blog.common.R;
import com.iris.blog.common.PageReq;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author lstar
 * @date: 2024-04
 * @description: 系统管理-用户信息
 */
@Api(tags = "系统管理-用户信息管理")
@Validated
@RestController
@RequestMapping("/system/userinfo")
public class UserInfoController {
    @Autowired
    private UserInfoService userInfoService;

    @ApiOperation(value = "用户信息列表", httpMethod = "POST", response = R.class, notes = "用户信息列表")
    @SaCheckPermission("system:user:query")
    @PostMapping("/list")
    public R selectUserInfoList(@RequestBody @Valid  PageReq<UserInfoDTO> req){

        return userInfoService.selectUserInfoList(req);
    }

    @ApiOperation(value = "用户信息信息", httpMethod = "GET", response = R.class, notes = "用户信息信息")
    @SaCheckPermission("system:user:query")
    @GetMapping("/info")
    public R selectUserInfoById( Long id){

        return userInfoService.selectUserInfoById(id);
    }

    @ApiOperation(value = "保存用户信息", httpMethod = "POST", response = R.class, notes = "保存用户信息")
    @SaCheckPermission("system:user:save")
    @OperateLog(value = "保存用户信息")
    @PostMapping("/save")
    public R saveUserInfo(@RequestBody @Valid  UserInfoDTO userInfo){

        return userInfoService.saveUserInfo(userInfo);
    }

    @ApiOperation(value = "修改用户信息", httpMethod = "POST", response = R.class, notes = "修改用户信息")
    @SaCheckPermission("system:user:update")
    @OperateLog(value = "修改用户信息")
    @PostMapping("/update")
    public R updateUserInfo(@RequestBody @Valid  UserInfoDTO userInfo){

        return userInfoService.updateUserInfo(userInfo);
    }


    @ApiOperation(value = "删除用户信息", httpMethod = "POST", response = R.class, notes = "删除用户信息")
    @SaCheckPermission("system:user:delete")
    @OperateLog(value = "删除用户信息")
    @PostMapping("/delete")
    public R removeUserInfoByIds(@RequestBody List<Long> ids){

        return userInfoService.removeUserInfoByIds(ids);
    }

}
