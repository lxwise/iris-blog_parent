package com.iris.blog.dao.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * @author lstar
 * @date: 2024-04
 * @description: 定时任务调度日志表
 */
@Data
@TableName("t_schedule_job_log")
@ApiModel(value="定时任务调度日志表")
public class ScheduleJobLogEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 任务日志ID
	 */
	@TableId(value = "id")
	@ApiModelProperty(value = "任务日志ID")
	private Long id;
	/**
	 * 任务ID
	 */
	
	@TableField(value = "job_id")
	@ApiModelProperty(value = "任务ID")
	private Long jobId;
	/**
	 * 任务名称
	 */
	
	@TableField(value = "job_name",condition = SqlCondition.LIKE)
	@ApiModelProperty(value = "任务名称")
	private String jobName;
	/**
	 * 任务组名
	 */
	
	@TableField(value = "job_group",condition = SqlCondition.LIKE)
	@ApiModelProperty(value = "任务组名")
	private String jobGroup;
	/**
	 * 调用目标字符串
	 */
	
	@TableField(value = "invoke_target")
	@ApiModelProperty(value = "调用目标字符串")
	private String invokeTarget;
	/**
	 * 日志信息
	 */
	
	@TableField(value = "job_message")
	@ApiModelProperty(value = "日志信息")
	private String jobMessage;
	/**
	 * 执行状态（0正常 1失败）
	 */
	
	@TableField(value = "status")
	@ApiModelProperty(value = "执行状态（0正常 1失败）")
	private Integer status;
	/**
	 * 异常信息
	 */
	
	@TableField(value = "exception_info")
	@ApiModelProperty(value = "异常信息")
	private String exceptionInfo;
	/**
	 * 创建时间
	 */
	
	@TableField(value = "create_time", fill = FieldFill.INSERT)
	@ApiModelProperty(value = "创建时间")
	private LocalDateTime createTime;

	/**
	 * 更新时间
	 */

	@TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
	@ApiModelProperty(value = "更新时间")
	private LocalDateTime updateTime;
	/**
	 * 开始时间
	 */
	
	@TableField(value = "start_time")
	@ApiModelProperty(value = "开始时间")
	private LocalDateTime startTime;
	/**
	 * 结束时间
	 */
	
	@TableField(value = "stop_time")
	@ApiModelProperty(value = "结束时间")
	private LocalDateTime stopTime;

	/**
	 * 执行时长 ms
	 */

	@TableField(value = "duration")
	@ApiModelProperty(value = "执行时长 ms）")
	private Long duration;
}
