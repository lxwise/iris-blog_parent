package com.iris.blog.domain.search;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author lstar
 * @date: 2024-04
 * @description: 定时任务调度日志表
 */
@Data
@ApiModel(value="定时任务调度日志表")
public class SearchScheduleJobLogDTO implements Serializable {
	private static final long serialVersionUID = 1L;


	@ApiModelProperty(value = "任务ID")
	private Long jobId;

	@ApiModelProperty(value = "任务名称")
	private String jobName;

	@ApiModelProperty(value = "任务组名")
	private String jobGroup;

	@ApiModelProperty(value = "执行状态（0正常 1失败）")
	private Integer status;

	@ApiModelProperty(value = "开始时间")
	private LocalDateTime startDateTime;

	@ApiModelProperty(value = "结束时间")
	private LocalDateTime stopDateTime;

}
