package com.iris.blog.controller.system;

import java.util.List;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import com.iris.blog.common.annotation.OperateLog;
import org.springframework.beans.factory.annotation.Autowired;
import javax.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.iris.blog.domain.dto.OperateLogDTO;
import com.iris.blog.service.OperateLogService;
import com.iris.blog.common.R;
import com.iris.blog.common.PageReq;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author lstar
 * @date: 2024-04
 * @description: 系统操作日志
 */

@Api(tags = "系统操作日志管理")
@Validated
@RestController
@RequestMapping("/system/log")
public class OperateLogController {
    @Autowired
    private OperateLogService operateLogService;

    @ApiOperation(value = "操作日志列表", httpMethod = "POST", response = R.class, notes = "操作日志列表")
    @SaCheckLogin
    @PostMapping("/list")
    public R selectOperateLogList(@RequestBody @Valid  PageReq<OperateLogDTO> req){

        return operateLogService.selectOperateLogList(req);
    }

    @ApiOperation(value = "操作日志信息", httpMethod = "GET", response = R.class, notes = "操作日志信息")
    @SaCheckLogin
    @GetMapping("/info")
    public R selectOperateLogById( Long id){

        return operateLogService.selectOperateLogById(id);
    }

//    @ApiOperation(value = "删除操作日志", httpMethod = "POST", response = R.class, notes = "删除操作日志")
//    @SaCheckPermission(value = "system:log:delete")
//    @OperateLog(value = "删除操作日志")
//    @PostMapping("/delete")
//    public R removeOperateLogByIds(@RequestBody List<Long> ids){
//
//        return operateLogService.removeOperateLogByIds(ids);
//    }

}
