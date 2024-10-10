package com.iris.blog.controller.system;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import com.iris.blog.common.annotation.OperateLog;
import com.iris.blog.domain.search.SearchSysConfigDTO;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.iris.blog.dao.entity.SysConfigEntity;
import com.iris.blog.domain.dto.SysConfigDTO;
import com.iris.blog.service.SysConfigService;
import com.iris.blog.common.R;
import com.iris.blog.common.PageReq;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author lstar
 * @date: 2024-04
 * @description: 系统参数管理
 */

@Api(tags = "系统参数管理")
@Validated
@RestController
@RequestMapping("/system/config")
public class SysConfigController {
    @Resource
    private SysConfigService sysConfigService;

    @ApiOperation(value = "系统参数列表", httpMethod = "POST", response = R.class, notes = "系统参数列表")
    @SaCheckPermission("system:config:query")
    @PostMapping("/list")
    public R selectSysConfigList(@RequestBody @Valid  PageReq<SearchSysConfigDTO> req){

        return sysConfigService.selectSysConfigList(req);
    }

    @ApiOperation(value = "系统参数信息", httpMethod = "GET", response = R.class, notes = "系统参数信息")
    @SaCheckPermission("system:config:query")
    @GetMapping("/info")
    public R selectSysConfigById( Long id){

        return sysConfigService.selectSysConfigById(id);
    }

    @ApiOperation(value = "保存系统参数", httpMethod = "POST", response = R.class, notes = "保存系统参数")
    @SaCheckPermission("system:config:save")
    @OperateLog(value = "保存系统参数")
    @PostMapping("/save")
    public R saveSysConfig(@RequestBody @Valid  SysConfigDTO sysConfig){

        return sysConfigService.saveSysConfig(sysConfig);
    }

    @ApiOperation(value = "修改系统参数", httpMethod = "POST", response = R.class, notes = "修改系统参数")
    @SaCheckPermission("system:config:update")
    @OperateLog(value = "修改系统参数")
    @PostMapping("/update")
    public R updateSysConfig(@RequestBody @Valid  SysConfigDTO sysConfig){

        return sysConfigService.updateSysConfig(sysConfig);
    }


    @ApiOperation(value = "删除系统参数", httpMethod = "GET", response = R.class, notes = "删除系统参数")
    @SaCheckPermission("system:config:delete")
    @OperateLog(value = "删除系统参数")
    @GetMapping("/delete")
    public R removeSysConfigByIds(@NotNull Long id){

        return sysConfigService.removeSysConfigByIds(id);
    }

    @ApiOperation(value = "修改系统参数状态", httpMethod = "GET", response = R.class, notes = "修改系统参数状态")
    @SaCheckPermission("system:config:update")
    @OperateLog(value = "修改系统参数状态")
    @GetMapping("/update/status")
    public R updateStatusById(@NotNull Long id){

        return sysConfigService.updateStatusById(id);
    }
    @PostMapping("/export")
    @SaCheckPermission("system:config:export")
    @OperateLog(value = "导出系统参数")
    @ApiOperation(value = "导出系统参数", httpMethod = "POST", response = R.class, notes = "导出")
    public void export(@RequestBody @Valid SearchSysConfigDTO paramCode, HttpServletResponse response) {
         this.sysConfigService.export(paramCode,response);
    }

    @ApiOperation(value = "根据参数编码，获取参数值", httpMethod = "GET", response = R.class, notes = "根据参数编码，获取参数值")
    @SaCheckLogin
    @GetMapping("/get/code/value")
    public R getValueByCode(@NotNull String paramCode){
        return R.ok(sysConfigService.getValueByCode(paramCode));
    }

    @ApiOperation(value = "根据参数编码，获取参数值,从redis", httpMethod = "GET", response = R.class, notes = "根据参数编码，获取参数值")
    @SaCheckLogin
    @GetMapping("/get/code/value/redis")
    public R getValueByCodeByRedis(@NotNull String paramCode){
        return R.ok(sysConfigService.getValueByCodeByRedis(paramCode));
    }

    @ApiOperation(value = "根据参数编码，更新参数值", httpMethod = "GET", response = R.class, notes = "根据参数编码，更新参数值")
    @SaCheckPermission("system:config:update")
    @OperateLog(value = "根据参数编码，更新参数值")
    @GetMapping("/update/code/value")
    public R updateValueByCode(@NotNull String paramCode, @NotNull String paramValue){
        return this.sysConfigService.updateValueByCode(paramCode, paramValue);
    }
}
