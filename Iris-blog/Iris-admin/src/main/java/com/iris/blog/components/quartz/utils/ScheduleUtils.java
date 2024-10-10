package com.iris.blog.components.quartz.utils;

import com.iris.blog.common.ResultCode;
import com.iris.blog.common.exception.BusinessException;
import com.iris.blog.dao.entity.ScheduleJobEntity;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;

/**
 * @author lstar
 * @date: 2024-04
 * @description: 定时任务工具类
 */
@Slf4j
public class ScheduleUtils {

    private final static String JOB_NAME = "TASK_";
    /**
     * 任务调度参数key
     */
    public static final String JOB_PARAM_KEY = "JOB_PARAM_KEY";

/**********************计划执行错误策略*******************************************/
    /** 默认 */
    public static final int MISFIRE_DEFAULT = 0;

    /** 立即触发执行 */
    public static final int MISFIRE_IGNORE_MISFIRES = 1;

    /** 触发一次执行 */
    public static final int MISFIRE_FIRE_AND_PROCEED = 2;

    /** 不触发立即执行 */
    public static final int MISFIRE_DO_NOTHING = 3;

    /**
     * 获取触发器key
     */
    public static TriggerKey getTriggerKey(Long jobId) {
        return TriggerKey.triggerKey(JOB_NAME + jobId);
    }
    
    /**
     * 获取jobKey
     */
    public static JobKey getJobKey(Long jobId) {
        return JobKey.jobKey(JOB_NAME + jobId);
    }

    /**
     * 获取表达式触发器
     */
    public static CronTrigger getCronTrigger(Scheduler scheduler, Long jobId) {
        try {
            return (CronTrigger) scheduler.getTrigger(getTriggerKey(jobId));
        } catch (SchedulerException e) {
            throw new BusinessException(ResultCode.SCHEDULE_JOB_ERROR);
        }
    }

    /**
     * 创建定时任务
     */
    public static void createScheduleJob(Scheduler scheduler, ScheduleJobEntity scheduleJob) {
        try {
        	//构建job信息
            JobDetail jobDetail = JobBuilder.newJob(ScheduleJob.class).withIdentity(getJobKey(scheduleJob.getId())).build();

            //表达式调度构建器
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(scheduleJob.getCronExpression());
            scheduleBuilder = handleCronScheduleMisfirePolicy(scheduleJob, scheduleBuilder);


            //按新的cronExpression表达式构建一个新的trigger
            CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(getTriggerKey(scheduleJob.getId())).withSchedule(scheduleBuilder).build();

            //放入参数，运行时的方法可以获取
            jobDetail.getJobDataMap().put(JOB_PARAM_KEY, scheduleJob);

            scheduler.scheduleJob(jobDetail, trigger);
            
            //暂停任务
            if(scheduleJob.getStatus() == ScheduleStatusEnum.PAUSE.value()){
            	pauseJob(scheduler, scheduleJob.getId());
            }
        } catch (SchedulerException e) {
            log.error("创建定时任务失败", e);
            throw new BusinessException(ResultCode.SCHEDULE_JOB_ERROR);
        }
    }

    private static CronScheduleBuilder handleCronScheduleMisfirePolicy(ScheduleJobEntity job, CronScheduleBuilder cb) {
        switch (job.getMisfirePolicy()) {
            case MISFIRE_DEFAULT:
                return cb;
            case MISFIRE_IGNORE_MISFIRES:
                return cb.withMisfireHandlingInstructionIgnoreMisfires();
            case MISFIRE_FIRE_AND_PROCEED:
                return cb.withMisfireHandlingInstructionFireAndProceed();
            case MISFIRE_DO_NOTHING:
                return cb.withMisfireHandlingInstructionDoNothing();
            default:
                throw new BusinessException("The task misfire policy '" + job.getMisfirePolicy()
                        + "' cannot be used in cron schedule tasks");
        }
    }

    /**
     * 更新定时任务
     */
    public static void updateScheduleJob(Scheduler scheduler, ScheduleJobEntity scheduleJob) {
        try {
            TriggerKey triggerKey = getTriggerKey(scheduleJob.getId());

            //表达式调度构建器
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(scheduleJob.getCronExpression())
            		.withMisfireHandlingInstructionDoNothing();

            CronTrigger trigger = getCronTrigger(scheduler, scheduleJob.getId());
            
            //按新的cronExpression表达式重新构建trigger
            trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();
            
            //参数
            trigger.getJobDataMap().put(JOB_PARAM_KEY, scheduleJob);
            
            scheduler.rescheduleJob(triggerKey, trigger);
            
            //暂停任务
            if(scheduleJob.getStatus() == ScheduleStatusEnum.PAUSE.value()){
            	pauseJob(scheduler, scheduleJob.getId());
            }
            
        } catch (Exception e) {
            log.error("更新定时任务失败", e);
            throw new BusinessException(ResultCode.SCHEDULE_JOB_ERROR);
        }
    }

    /**
     * 立即执行任务
     */
    public static void run(Scheduler scheduler, ScheduleJobEntity scheduleJob) {
        try {
        	//参数
        	JobDataMap dataMap = new JobDataMap();
        	dataMap.put(JOB_PARAM_KEY, scheduleJob);
        	
            scheduler.triggerJob(getJobKey(scheduleJob.getId()), dataMap);
        } catch (SchedulerException e) {
            log.error("立即执行定时任务失败:", e);
            throw new BusinessException(ResultCode.SCHEDULE_JOB_ERROR);
        }
    }

    /**
     * 暂停任务
     */
    public static void pauseJob(Scheduler scheduler, Long jobId) {
        try {
            scheduler.pauseJob(getJobKey(jobId));
        } catch (SchedulerException e) {
            log.error("暂停定时任务失败:", e);
            throw new BusinessException(ResultCode.SCHEDULE_JOB_ERROR);
        }
    }

    /**
     * 恢复任务
     */
    public static void resumeJob(Scheduler scheduler, Long jobId) {
        try {
            scheduler.resumeJob(getJobKey(jobId));
        } catch (SchedulerException e) {
            log.error("恢复定时任务失败:", e);
            throw new BusinessException(ResultCode.SCHEDULE_JOB_ERROR);
        }
    }

    /**
     * 删除定时任务
     */
    public static void deleteScheduleJob(Scheduler scheduler, Long jobId) {
        try {
            scheduler.deleteJob(getJobKey(jobId));
        } catch (SchedulerException e) {
            log.error("删除定时任务失败:", e);
            throw new BusinessException(ResultCode.SCHEDULE_JOB_ERROR);
        }
    }
}