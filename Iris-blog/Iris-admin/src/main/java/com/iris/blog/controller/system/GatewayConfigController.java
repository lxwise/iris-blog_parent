package com.iris.blog.controller.system;

import java.util.List;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.iris.blog.common.annotation.OperateLog;
import org.springframework.beans.factory.annotation.Autowired;
import javax.validation.Valid;
import org.springframework.validation.annotation.Validated;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.*;
import com.iris.blog.dao.entity.GatewayConfigEntity;
import com.iris.blog.domain.dto.GatewayConfigDTO;
import com.iris.blog.service.GatewayConfigService;
import com.iris.blog.common.R;
import com.iris.blog.common.PageReq;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.servlet.http.HttpServletResponse;
/**
 * @author lstar
 * @date: 2024-06
 * @description: 网关配置
 */

@Api(tags = "网关配置")
@Validated
@RestController
@RequestMapping("/system/gateway/config")
public class GatewayConfigController {
    @Autowired
    private GatewayConfigService gatewayConfigService;

    @ApiOperation(value = "网关配置列表", httpMethod = "POST", response = R.class, notes = "网关配置列表")
    @SaCheckPermission("gateway:config:query")
    @PostMapping("/list")
    public R selectGatewayConfigList(@RequestBody @Valid  PageReq<GatewayConfigDTO> req){

        return gatewayConfigService.selectGatewayConfigList(req);
    }

    @ApiOperation(value = "网关配置信息", httpMethod = "GET", response = R.class, notes = "网关配置信息")
    @SaCheckPermission("gateway:config:query")
    @GetMapping("/info")
    public R selectGatewayConfigById(@NotNull Long id){

        return gatewayConfigService.selectGatewayConfigById(id);
    }

    @ApiOperation(value = "保存网关配置", httpMethod = "POST", response = R.class, notes = "保存网关配置")
    @SaCheckPermission("gateway:config:save")
    @OperateLog(value = "保存网关配置")
    @PostMapping("/save")
    public R saveGatewayConfig(@RequestBody @Valid  GatewayConfigDTO gatewayConfig){

        return gatewayConfigService.saveGatewayConfig(gatewayConfig);
    }

    @ApiOperation(value = "修改网关配置", httpMethod = "POST", response = R.class, notes = "修改网关配置")
    @SaCheckPermission("gateway:config:update")
    @OperateLog(value = "修改网关配置")
    @PostMapping("/update")
    public R updateGatewayConfig(@RequestBody @Valid  GatewayConfigDTO gatewayConfig){

        return gatewayConfigService.updateGatewayConfig(gatewayConfig);
    }


    @ApiOperation(value = "删除网关配置", httpMethod = "POST", response = R.class, notes = "删除网关配置")
    @SaCheckPermission("gateway:config:delete")
    @OperateLog(value = "删除网关配置")
    @PostMapping("/delete")
    public R removeGatewayConfigByIds(@RequestBody @NotEmpty List<Long> ids){

        return gatewayConfigService.removeGatewayConfigByIds(ids);
    }
//    @PostMapping("/export")
//    @ApiOperation(value = "导出", httpMethod = "POST", response = R.class, notes = "导出")
//    public void export(@RequestBody @Valid GatewayConfigDTO gatewayConfig, HttpServletResponse response) {
//        this.gatewayConfigService.export(gatewayConfig,response);
//    }

}
