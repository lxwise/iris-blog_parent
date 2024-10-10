package com.iris.blog.controller.system;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.iris.blog.common.R;
import com.iris.blog.service.MonitorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;

/**
 * @author lstar
 * @date: 2024-04
 * @description: 系统管理-服务监控
 */
@Api(tags = "系统管理-监控管理")
@Validated
@RestController
@RequestMapping("/system/monitor")
public class MonitorController {

    @Resource
    private MonitorService monitorService;

    @GetMapping(value = "/systemInfo")
    @SaCheckPermission("system:monitor:systemInfo")
    @ApiOperation(value = "服务器监控", httpMethod = "GET", response = R.class, notes = "服务器监控")
    public R getSystemServerInfo() {
        return monitorService.getSystemServerInfo();
    }

    @GetMapping(value = "/cacheInfo")
    @SaCheckPermission("system:monitor:cacheInfo")
    @ApiOperation(value = "redis监控", httpMethod = "GET", response = R.class, notes = "redis监控")
    public R getRedisMonitorInfo(){
        return monitorService.getRedisMonitorInfo();
    }
    @GetMapping(value = "/serviceInfo")
    @SaCheckPermission("system:monitor:serviceInfo")
    @ApiOperation(value = "java服务监控", httpMethod = "GET", response = R.class, notes = "java服务监控")
    public R getServiceInfo(){
        return monitorService.getServiceInfo();
    }
}
