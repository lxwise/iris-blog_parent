package com.iris.blog.controller.system;

import java.util.List;

import cn.dev33.satoken.annotation.SaCheckPermission;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.springframework.validation.annotation.Validated;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.*;
import com.iris.blog.dao.entity.GatewayLogEntity;
import com.iris.blog.domain.dto.GatewayLogDTO;
import com.iris.blog.service.GatewayLogService;
import com.iris.blog.common.R;
import com.iris.blog.common.PageReq;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author lstar
 * @date: 2024-06
 * @description: 网关日志
 */

@Api(tags = "网关日志")
@Validated
@RestController
@RequestMapping("/system/gateway/log")
public class GatewayLogController {
    @Autowired
    private GatewayLogService gatewayLogService;

    @ApiOperation(value = "网关日志列表", httpMethod = "POST", response = R.class, notes = "网关日志列表")
    @SaCheckPermission("gateway:log:query")
    @PostMapping("/list")
    public R selectGatewayLogList(@RequestBody @Valid  PageReq<GatewayLogDTO> req){

        return gatewayLogService.selectGatewayLogList(req);
    }

    @ApiOperation(value = "网关日志信息", httpMethod = "GET", response = R.class, notes = "网关日志信息")
    @SaCheckPermission("gateway:log:query")
    @GetMapping("/info")
    public R selectGatewayLogById(@NotNull Long id){

        return gatewayLogService.selectGatewayLogById(id);
    }
    @ApiOperation(value = "网关访问IP", httpMethod = "GET", response = R.class, notes = "网关访问IP")
    @SaCheckPermission("gateway:log:query")
    @GetMapping("/ip")
    public R selectGatewayIPList(){

        return gatewayLogService.selectGatewayIPList();
    }

//    @PostMapping("/export")
//    @ApiOperation(value = "导出", httpMethod = "POST", response = R.class, notes = "导出")
//    public void export(@RequestBody @Valid GatewayLogDTO gatewayLog, HttpServletResponse response) {
//        this.gatewayLogService.export(gatewayLog,response);
//    }

}
