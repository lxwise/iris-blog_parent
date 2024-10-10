package com.iris.blog.domain.vo;

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
@ApiModel(value="定时任务调度日志表")
public class ScheduleJobLogVO implements Serializable {
	private static final long serialVersionUID = 1L;


	@ApiModelProperty(value = "任务日志ID")
	private Long id;

	@ApiModelProperty(value = "任务ID")
	private Long jobId;

	@ApiModelProperty(value = "任务名称")
	private String jobName;

	@ApiModelProperty(value = "任务组名")
	private String jobGroup;

	@ApiModelProperty(value = "调用目标字符串")
	private String invokeTarget;

	@ApiModelProperty(value = "日志信息")
	private String jobMessage;

	@ApiModelProperty(value = "执行状态（0正常 1失败）")
	private Integer status;

	@ApiModelProperty(value = "异常信息")
	private String exceptionInfo;

	@ApiModelProperty(value = "创建时间")
	private LocalDateTime createTime;

	@ApiModelProperty(value = "更新时间")
	private LocalDateTime updateTime;

	@ApiModelProperty(value = "开始时间")
	private LocalDateTime startTime;

	@ApiModelProperty(value = "结束时间")
	private LocalDateTime stopTime;

	@ApiModelProperty(value = "执行时长 ms）")
	private Long duration;

}
