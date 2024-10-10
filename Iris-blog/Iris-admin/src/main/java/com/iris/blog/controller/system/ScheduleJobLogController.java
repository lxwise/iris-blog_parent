package com.iris.blog.controller.system;

import java.util.List;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaMode;
import com.iris.blog.common.annotation.OperateLog;
import com.iris.blog.domain.search.SearchScheduleJobLogDTO;
import org.springframework.beans.factory.annotation.Autowired;
import javax.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.iris.blog.dao.entity.ScheduleJobLogEntity;
import com.iris.blog.domain.dto.ScheduleJobLogDTO;
import com.iris.blog.service.ScheduleJobLogService;
import com.iris.blog.common.R;
import com.iris.blog.common.PageReq;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author lstar
 * @date: 2024-04
 * @description: 定时任务调度日志表
 */

@Api(tags = "定时任务调度日志管理")
@Validated
@RestController
@RequestMapping("/system/job/log")
public class ScheduleJobLogController {
    @Autowired
    private ScheduleJobLogService scheduleJobLogService;

    @ApiOperation(value = "定时任务调度日志列表", httpMethod = "POST", response = R.class, notes = "定时任务调度日志列表")
    @SaCheckPermission(value = {"system:jobtask:query", "system:joblog:query"}, mode = SaMode.OR)
    @PostMapping("/list")
    public R selectScheduleJobLogList(@RequestBody @Valid  PageReq<SearchScheduleJobLogDTO> req){

        return scheduleJobLogService.selectScheduleJobLogList(req);
    }

    @ApiOperation(value = "定时任务调度日志信息", httpMethod = "GET", response = R.class, notes = "定时任务调度日志信息")
    @GetMapping("/info")
    public R selectScheduleJobLogById( Long id){

        return scheduleJobLogService.selectScheduleJobLogById(id);
    }
    @ApiOperation(value = "删除定时任务调度日志", httpMethod = "POST", response = R.class, notes = "删除定时任务调度日志")
    @SaCheckPermission("system:joblog:delete")
    @OperateLog(value = "删除定时任务调度日志")
    @PostMapping("/delete")
    public R removeScheduleJobLogByIds(@RequestBody List<Long> ids){

        return scheduleJobLogService.removeScheduleJobLogByIds(ids);
    }

}
