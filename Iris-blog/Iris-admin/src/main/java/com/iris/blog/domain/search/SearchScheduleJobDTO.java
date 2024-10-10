package com.iris.blog.domain.search;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author lstar
 * @date: 2024-04
 * @description: 定时任务调度表
 */
@Data
@ApiModel(value="定时任务调度表")
public class SearchScheduleJobDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "任务名称")
	private String jobName;

	@ApiModelProperty(value = "任务组名")
	private String jobGroup;

	@ApiModelProperty(value = "状态（0暂停 1正常）")
	private Integer status;

}
