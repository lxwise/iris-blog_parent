package com.iris.blog.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * @author lstar
 * @date: 2024-04
 * @description: 定时任务调度表
 */
@Data
@ApiModel(value="定时任务调度表")
public class ScheduleJobDTO implements Serializable {
	private static final long serialVersionUID = 1L;


	@ApiModelProperty(value = "任务ID")
	private Long id;

	@ApiModelProperty(value = "任务名称")
	private String jobName;

	@ApiModelProperty(value = "任务组名")
	private String jobGroup;

	@ApiModelProperty(value = "调用目标字符串")
	private String invokeTarget;

	@ApiModelProperty(value = "cron执行表达式")
	private String cronExpression;

	@ApiModelProperty(value = "计划执行错误策略（1立即执行 2执行一次 3放弃执行）")
	private Integer misfirePolicy;

	@ApiModelProperty(value = "状态（0暂停 1正常）")
	private Integer status;

	@ApiModelProperty(value = "备注信息")
	private String remark;

}
