package com.iris.blog.components.quartz.utils;

import com.iris.blog.dao.entity.ScheduleJobEntity;
import com.iris.blog.dao.entity.ScheduleJobLogEntity;
import com.iris.blog.service.ScheduleJobLogService;
import com.iris.blog.utils.DateUtil;
import com.iris.blog.utils.SpringContextUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.quartz.JobExecutionContext;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * @author lstar
 * @date: 2024-04
 * @description: 定时任务
 */
@Slf4j
public class ScheduleJob implements org.quartz.Job{

	@Override
	public void execute(JobExecutionContext context) {
		ScheduleJobEntity job = (ScheduleJobEntity) context.getMergedJobDataMap().
				get(ScheduleUtils.JOB_PARAM_KEY);
		//数据库保存执行记录
		ScheduleJobLogEntity jobLog = new ScheduleJobLogEntity();
		jobLog.setStartTime(LocalDateTime.now());
		try {
			ScheduleJobUtil.invokeMethod(job);
			after(context, job, jobLog,null);
		} catch (Exception e) {
			log.error("任务执行异常  - ：", e);
			after(context, job,jobLog, e);
		}
	}

	/**
	 * 执行后
	 *
	 * @param context 工作执行上下文对象
	 * @param job  系统计划任务
	 * @param jobLog  系统计划任务日志
	 * @param e  异常信息
	 */
	protected void after(JobExecutionContext context, ScheduleJobEntity job,ScheduleJobLogEntity jobLog, Exception e) {

		jobLog.setJobId(job.getId());
		jobLog.setJobName(job.getJobName());
		jobLog.setJobGroup(job.getJobGroup());
		jobLog.setInvokeTarget(job.getInvokeTarget());
		jobLog.setStopTime(LocalDateTime.now());
		long runMs = DateUtil.getChronoUnitBetween(jobLog.getStartTime(),jobLog.getStopTime(), ChronoUnit.MILLIS);
		jobLog.setJobMessage(jobLog.getJobName() + " 总共耗时：" + runMs + "毫秒");
		jobLog.setDuration(runMs);
		if (e != null) {
			jobLog.setStatus(ScheduleStatusEnum.NORMAL.value());
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw, true));
			String str = sw.toString();
			String errorMsg = StringUtils.substring(str, 0, 2000);
			jobLog.setExceptionInfo(errorMsg);
		} else {
			jobLog.setStatus(ScheduleStatusEnum.PAUSE.value());
		}
		// 写入数据库当中
		//获取spring bean
		ScheduleJobLogService scheduleJobLogService = SpringContextUtils.getBean(ScheduleJobLogService.class);
		scheduleJobLogService.save(jobLog);


	}
}