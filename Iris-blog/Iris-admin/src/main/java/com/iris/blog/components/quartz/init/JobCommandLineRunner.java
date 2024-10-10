package com.iris.blog.components.quartz.init;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.iris.blog.dao.entity.ScheduleJobEntity;
import com.iris.blog.components.quartz.utils.ScheduleStatusEnum;
import com.iris.blog.components.quartz.utils.ScheduleUtils;
import com.iris.blog.service.ScheduleJobService;
import org.quartz.CronTrigger;
import org.quartz.Scheduler;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

 /**
 * @author lstar
 * @date: 2024-04
 * @description: 初始化定时任务数据
 */
@Component
public class JobCommandLineRunner implements CommandLineRunner {
    @Resource
    private Scheduler scheduler;
    @Resource
    private ScheduleJobService scheduleJobService;

    @Override
    public void run(String... args) {
        LambdaQueryWrapper<ScheduleJobEntity> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(ScheduleJobEntity::getStatus, ScheduleStatusEnum.NORMAL.value());
        List<ScheduleJobEntity> scheduleJobList = scheduleJobService.list(queryWrapper);
        for(ScheduleJobEntity scheduleJob : scheduleJobList){
            CronTrigger cronTrigger = ScheduleUtils.getCronTrigger(scheduler, scheduleJob.getId());
            //如果不存在，则创建
            if(cronTrigger == null) {
                ScheduleUtils.createScheduleJob(scheduler, scheduleJob);
            }else {
                ScheduleUtils.updateScheduleJob(scheduler, scheduleJob);
            }
        }
    }
}