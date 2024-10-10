package com.iris.blog.controller.system;

import java.util.List;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.iris.blog.common.annotation.OperateLog;
import com.iris.blog.domain.search.SearchScheduleJobDTO;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import javax.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.iris.blog.dao.entity.ScheduleJobEntity;
import com.iris.blog.domain.dto.ScheduleJobDTO;
import com.iris.blog.service.ScheduleJobService;
import com.iris.blog.common.R;
import com.iris.blog.common.PageReq;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author lstar
 * @date: 2024-04
 * @description: 定时任务调度表
 */

@Api(tags = "定时任务调度管理")
@Validated
@RestController
@RequestMapping("/system/job")
public class ScheduleJobController {
    @Autowired
    private ScheduleJobService scheduleJobService;

    @ApiOperation(value = "定时任务列表", httpMethod = "POST", response = R.class, notes = "定时任务列表")
    @SaCheckPermission("system:jobtask:query")
    @PostMapping("/list")
    public R selectScheduleJobList(@RequestBody @Valid  PageReq<SearchScheduleJobDTO> req){

        return scheduleJobService.selectScheduleJobList(req);
    }

    @ApiOperation(value = "定时任务详情", httpMethod = "GET", response = R.class, notes = "定时任务详情")
    @SaCheckPermission("system:jobtask:query")
    @GetMapping("/info")
    public R selectScheduleJobById( Long id){

        return scheduleJobService.selectScheduleJobById(id);
    }

    @ApiOperation(value = "添加定时任务", httpMethod = "POST", response = R.class, notes = "添加定时任务")
    @SaCheckPermission("system:jobtask:save")
    @PostMapping("/save")
    @OperateLog(value = "添加定时任务")
    public R saveScheduleJob(@RequestBody @Valid  ScheduleJobDTO scheduleJob){

        return scheduleJobService.saveScheduleJob(scheduleJob);
    }

    @ApiOperation(value = "修改定时任务", httpMethod = "POST", response = R.class, notes = "修改定时任务")
    @SaCheckPermission("system:jobtask:update")
    @PostMapping("/update")
    @OperateLog(value = "修改定时任务")
    public R updateScheduleJob(@RequestBody @Valid  ScheduleJobDTO scheduleJob){

        return scheduleJobService.updateScheduleJob(scheduleJob);
    }


    @ApiOperation(value = "删除定时任务", httpMethod = "GET", response = R.class, notes = "删除定时任务")
    @SaCheckPermission("system:jobtask:delete")
    @GetMapping("/delete")
    @OperateLog(value = "删除定时任务")
    public R removeScheduleJobByIds(Long id){

        return scheduleJobService.removeScheduleJobByIds(id);
    }

    @GetMapping(value = "/run")
    @ApiOperation(value = "立即执行定时任务", httpMethod = "GET", response = R.class, notes = "立即执行定时任务")
    @SaCheckPermission("system:jobtask:run")
    @OperateLog(value = "立即执行定时任务")
    public R run(Long id) {
        return scheduleJobService.run(id);
    }

    @GetMapping(value = "/change/status")
    @ApiOperation(value = "修改状态定时任务", httpMethod = "GET", response = R.class, notes = "修改状态定时任务")
    @SaCheckPermission("system:jobtask:update")
    @OperateLog(value = "修改状态定时任务")
    public R changeStatus(Long id){
        return scheduleJobService.changeStatus(id);
    }

}
